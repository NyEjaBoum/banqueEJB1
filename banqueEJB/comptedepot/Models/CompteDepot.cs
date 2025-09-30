using System.ComponentModel.DataAnnotations.Schema;

namespace comptedepot.Models
{
    [Table("compte_depot")]
    public class CompteDepot
    {
        [Column("id")]
        public int Id { get; set; }
        [Column("client_id")]
        public int ClientId { get; set; }
        [Column("solde")]
        public decimal Solde { get; set; }
        [Column("taux_interet")]
        public decimal? TauxInteret { get; set; }
        [Column("plafond_retrait")]
        public decimal? PlafondRetrait { get; set; }
        [Column("date_dernier_interet")]
        public DateTime DateDernierInteret { get; set; }
        [Column("actif")]
        public bool Actif { get; set; }
        // public Client Client { get; set; }
    }
}