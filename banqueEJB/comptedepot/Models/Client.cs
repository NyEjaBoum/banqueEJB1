using System.ComponentModel.DataAnnotations.Schema;

namespace comptedepot.Models
{
    [Table("client")]
    public class Client
    {
        [Column("id")]
        public int Id { get; set; }
        [Column("nom")]
        public string Nom { get; set; }
        [Column("prenom")]
        public string Prenom { get; set; }
        [Column("email")]
        public string Email { get; set; }
        [Column("telephone")]
        public string Telephone { get; set; }
        [Column("numero_client")]
        public int NumeroClient { get; set; }
    }
}