using Microsoft.EntityFrameworkCore;
using comptedepot.Models;

namespace comptedepot.Data
{
    public class AppDbContext : DbContext
    {
        // public DbSet<Client> Clients { get; set; }
        public DbSet<CompteDepot> ComptesDepot { get; set; }
        public DbSet<MouvementDepot> MouvementsDepot { get; set; }
        public DbSet<Parametre> Parametres { get; set; }

        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }
    }
}