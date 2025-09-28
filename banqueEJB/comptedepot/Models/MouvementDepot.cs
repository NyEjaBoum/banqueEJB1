using System.ComponentModel.DataAnnotations.Schema;

namespace comptedepot.Models
{
    [Table("mouvement_depot")]
    public class MouvementDepot
    {
        [Column("id")]
        public int Id { get; set; }
        [Column("compte_id")]
        public int CompteId { get; set; }
        [Column("montant")]
        public decimal Montant { get; set; }
        [Column("type")]
        public string Type { get; set; } // "VERSEMENT" ou "RETRAIT"
        [Column("date_mouvement")]
        public DateTime DateMouvement { get; set; }
        public CompteDepot Compte { get; set; }
    }
}