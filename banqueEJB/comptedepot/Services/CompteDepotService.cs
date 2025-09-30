using comptedepot.Models;
using comptedepot.Data;

namespace comptedepot.Services
{
    public class CompteDepotService
    {
        private readonly AppDbContext _context;

        public CompteDepotService(AppDbContext context)
        {
            _context = context;
        }

        public Parametre GetParametreGlobal()
        {
            var param = _context.Parametres.FirstOrDefault();
            if (param == null) throw new Exception("Aucun paramètre global défini.");
            return param;
        }

        // Règle 1 : Création du compte
        public CompteDepot CreerCompte(int clientId, decimal? plafondRetrait, decimal? tauxInteret)
        {
            // var client = _context.Clients.Find(clientId);
            // if (client == null) throw new Exception("Client introuvable");

            var compte = new CompteDepot
            {
                ClientId = clientId,
                Solde = 0,
                TauxInteret = tauxInteret,
                PlafondRetrait = plafondRetrait,
                DateDernierInteret = DateTime.UtcNow,
                Actif = false
            };
            _context.ComptesDepot.Add(compte);
            _context.SaveChanges();
            return compte;
        }

        // Règle 2, 3, 6 : Versement
        public MouvementDepot Verser(int compteId, decimal montant)
        {
            if (montant <= 0) throw new Exception("Montant doit être positif");
            var compte = _context.ComptesDepot.Find(compteId);
            if (compte == null) throw new Exception("Compte introuvable");

            compte.Solde += montant;
            if (!compte.Actif) compte.Actif = true; // Devient actif au premier versement

            var mvt = new MouvementDepot
            {
                CompteId = compteId,
                Montant = montant,
                TypeMouvementId = 4, // 4 = VERSEMENT
                DateMouvement = DateTime.UtcNow
            };
            _context.MouvementsDepot.Add(mvt);
            _context.SaveChanges();
            return mvt;
        }

        // Règle 3, 6 : Retrait
        public MouvementDepot Retirer(int compteId, decimal montant)
        {
            if (montant <= 0) throw new Exception("Montant doit être positif");
            var compte = _context.ComptesDepot.Find(compteId);
            if (compte == null) throw new Exception("Compte introuvable");

            var plafond = compte.PlafondRetrait ?? _context.Parametres.First().PlafondRetraitGlobal;
            if (montant > plafond) throw new Exception("Montant > plafond");
            if (montant > compte.Solde) throw new Exception("Solde insuffisant");

            compte.Solde -= montant;
            var mvt = new MouvementDepot
            {
                CompteId = compteId,
                Montant = montant,
                TypeMouvementId = 5, // 5 = RETRAIT
                DateMouvement = DateTime.UtcNow
            };
            _context.MouvementsDepot.Add(mvt);
            _context.SaveChanges();
            return mvt;
        }

        // Règle 4 : Calcul des intérêts
        public MouvementDepot AppliquerInteret(int compteId)
        {
            var compte = _context.ComptesDepot.Find(compteId);
            if (compte == null) throw new Exception("Compte introuvable");

            var taux = compte.TauxInteret ?? _context.Parametres.First().TauxInteretDepot;
            var interet = compte.Solde * taux / 100;
            compte.Solde += interet;
            compte.DateDernierInteret = DateTime.UtcNow;

            var mvt = new MouvementDepot
            {
                CompteId = compteId,
                Montant = interet,
                TypeMouvementId = 4, // 4 = VERSEMENT (intérêt = versement)
                DateMouvement = DateTime.UtcNow
            };
            _context.MouvementsDepot.Add(mvt);
            _context.SaveChanges();
            return mvt;
        }

        // Règle 5 : Solde et historique
        public decimal GetSolde(int compteId)
        {
            var compte = _context.ComptesDepot.Find(compteId);
            if (compte == null) throw new Exception("Compte introuvable");
            return compte.Solde;
        }

        public List<MouvementDepot> Historique(int compteId)
        {
            return _context.MouvementsDepot
                .Where(m => m.CompteId == compteId)
                .OrderBy(m => m.DateMouvement)
                .ToList();
        }

        // Règle 7 : Modifier plafond ou taux d’intérêt
        public CompteDepot ModifierPlafond(int compteId, decimal? nouveauPlafond)
        {
            var compte = _context.ComptesDepot.Find(compteId);
            if (compte == null) throw new Exception("Compte introuvable");
            compte.PlafondRetrait = nouveauPlafond;
            _context.SaveChanges();
            return compte;
        }

        public CompteDepot ModifierTauxInteret(int compteId, decimal? nouveauTaux)
        {
            var compte = _context.ComptesDepot.Find(compteId);
            if (compte == null) throw new Exception("Compte introuvable");
            compte.TauxInteret = nouveauTaux;
            _context.SaveChanges();
            return compte;
        }
    }
}