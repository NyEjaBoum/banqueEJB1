using Microsoft.AspNetCore.Mvc;
using comptedepot.Models;
using comptedepot.Services;

namespace comptedepot.Controllers
{
    [ApiController]
    [Route("api/depot")]
    public class CompteDepotController : ControllerBase
    {
        private readonly CompteDepotService _service;

        public CompteDepotController(CompteDepotService service)
        {
            _service = service;
        }

        [HttpGet("parametre")]
        public ActionResult<Parametre> GetParametreGlobal()
        {
            try
            {
                var param = _service.GetParametreGlobal();
                return Ok(param);
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpPost("creer")]
        public ActionResult<CompteDepot> CreerCompte(int clientId, decimal? plafondRetrait, decimal? tauxInteret)
        {
            try
            {
                var compte = _service.CreerCompte(clientId, plafondRetrait, tauxInteret);
                return Ok(compte);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPost("versement")]
        public ActionResult<MouvementDepot> Verser(int compteId, decimal montant)
        {
            try
            {
                var mvt = _service.Verser(compteId, montant);
                return Ok(mvt);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPost("retrait")]
        public ActionResult<MouvementDepot> Retirer(int compteId, decimal montant)
        {
            try
            {
                var mvt = _service.Retirer(compteId, montant);
                return Ok(mvt);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPost("interet")]
        public ActionResult<MouvementDepot> AppliquerInteret(int compteId)
        {
            try
            {
                var mvt = _service.AppliquerInteret(compteId);
                return Ok(mvt);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("solde/{id}")]
        public ActionResult<decimal> GetSolde(int id)
        {
            try
            {
                var solde = _service.GetSolde(id);
                return Ok(solde);
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpGet("historique/{id}")]
        public ActionResult<List<MouvementDepot>> Historique(int id)
        {
            try
            {
                var histo = _service.Historique(id);
                return Ok(histo);
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        // Nouveau : Modifier le plafond de retrait d'un compte
        [HttpPut("plafond/{id}")]
        public ActionResult<CompteDepot> ModifierPlafond(int id, decimal? nouveauPlafond)
        {
            try
            {
                var compte = _service.ModifierPlafond(id, nouveauPlafond);
                return Ok(compte);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        // Nouveau : Modifier le taux d'intérêt d'un compte
        [HttpPut("taux/{id}")]
        public ActionResult<CompteDepot> ModifierTauxInteret(int id, decimal? nouveauTaux)
        {
            try
            {
                var compte = _service.ModifierTauxInteret(id, nouveauTaux);
                return Ok(compte);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}