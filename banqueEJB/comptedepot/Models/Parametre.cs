using System.ComponentModel.DataAnnotations.Schema;

namespace comptedepot.Models
{
    [Table("parametre")]
    public class Parametre
    {
        [Column("id")]
        public int Id { get; set; }
        [Column("plafond_retrait_global")]
        public decimal PlafondRetraitGlobal { get; set; }
        [Column("taux_interet_depot")]
        public decimal TauxInteretDepot { get; set; }
    }
}