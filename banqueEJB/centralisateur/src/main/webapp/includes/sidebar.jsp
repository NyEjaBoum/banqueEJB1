<!-- filepath: centralisateur/src/main/webapp/includes/sidebar.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="sidebar">
    <div class="sidebar-header">
        <h1>🏦 Banque EJB</h1>
    </div>
    <nav class="sidebar-nav">
        <div class="nav-section">
            <h3>Comptes</h3>
            <a href="${pageContext.request.contextPath}/compte_courant" class="nav-item">
                <i>💳</i> Comptes Courants
            </a>
            <a href="${pageContext.request.contextPath}/compte_depot" class="nav-item">
                <i>💰</i> Comptes Dépôts
            </a>
            <a href="${pageContext.request.contextPath}/depotOperation" class="nav-item">
                <i>🔄</i> Opérations Dépôt
            </a>
            <a href="${pageContext.request.contextPath}/depotParametre" class="nav-item">
                <i>⚙️</i> Paramètres Dépôt
            </a>
        </div>
        
        <div class="nav-section">
            <h3>Prêts</h3>
            <a href="${pageContext.request.contextPath}/pret" class="nav-item">
                <i>🏠</i> Gestion Prêts
            </a>
        </div>
        
        <div class="nav-section">
            <h3>Clients</h3>
            <a href="${pageContext.request.contextPath}/clients" class="nav-item">
                <i>👥</i> Gestion Clients
            </a>
        </div>
    </nav>
</div>