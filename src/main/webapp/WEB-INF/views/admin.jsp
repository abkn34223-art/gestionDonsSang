<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ma.ensa.model.Utilisateur" %>
<%  Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    if (utilisateur == null || !utilisateur.getRole().name().equals("ADMIN")) {
        response.sendRedirect(request.getContextPath() + "/connexion");
        return;}
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de bord | Gestion Dons de Sang</title>
    <style>
        * {margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Arial, sans-serif;}
        body {background: #f8f5f5;
            color: #292929;}
        .sidebar {
            position: fixed;
            width: 250px;
            height: 100vh;
            background: #8f1025;
            color: white;
            padding: 30px 20px;
        }
        .logo {
            font-size: 23px;
            font-weight: bold;
            margin-bottom: 45px;}
        .logo span {color: #ffb8c4;}
        .sidebar h3 {
            font-size: 12px;
            color: #ffcbd3;
            text-transform: uppercase;
            margin-bottom: 18px;
        }
        .sidebar a {
            display: block;
            color: white;
            text-decoration: none;
            padding: 13px 15px;
            margin-bottom: 10px;
            border-radius: 9px;
            transition: 0.3s;
        }
        .sidebar a:hover {
            background: #b52a43;
            transform: translateX(4px);
        }
        .logout {
            position: absolute;
            bottom: 30px;
            left: 20px;
            right: 20px;
            background: #720b1c;
        }
        .main {
            margin-left: 250px;
            padding: 35px 45px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 35px;
            gap: 15px;
        }
        .header h1 {
            color: #8f1025;
            font-size: 29px;
        }
        .header p {
            color: #777;
            margin-top: 8px;
        }
        .admin-badge {
            background: #ffe4e9;
            color: #8f1025;
            padding: 12px 18px;
            border-radius: 25px;
            font-weight: bold;
        }
        .welcome {
            background: linear-gradient(120deg, #8f1025, #c52d49);
            color: white;
            padding: 30px;
            border-radius: 18px;
            margin-bottom: 35px;
            box-shadow: 0 8px 20px rgba(143, 16, 37, 0.15);
        }
        .welcome h2 {
            font-size: 24px;
            margin-bottom: 10px;
        }
        .welcome p {
            color: #ffe4e9;
            line-height: 1.6;
        }
        .section-title {
            font-size: 21px;
            margin-bottom: 20px;
            color: #333;
        }
        .stats {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            margin-bottom: 35px;
        }
        .stat-card {
            background: white;
            padding: 23px;
            border-radius: 15px;
            border: 1px solid #f0e2e5;
            box-shadow: 0 4px 14px rgba(0,0,0,0.04);
            transition: 0.3s;
        }
        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(143,16,37,0.10);
        }
        .stat-icon {
            width: 45px;
            height: 45px;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #ffe8ec;
            border-radius: 12px;
            font-size: 23px;
            margin-bottom: 17px;
        }
        .stat-card h3 {
            color: #888;
            font-size: 14px;
            font-weight: 500;
            margin-bottom: 9px;
        }
        .stat-card .number {
            font-size: 30px;
            font-weight: bold;
            color: #8f1025;
        }
        .activity {
            background: white;
            padding: 25px;
            border-radius: 15px;
            margin-bottom: 35px;
            border: 1px solid #f0e2e5;
        }
        .activity p {
            padding: 13px 0;
            border-bottom: 1px solid #f1eeee;
            color: #555;
        }
        .activity p:last-child {border-bottom: none;}
        .activity strong {color: #8f1025;}
        .management {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }
        .management-card {
            background: white;
            padding: 25px;
            border-radius: 15px;
            border: 1px solid #f0e2e5;
            transition: 0.3s;
        }
        .management-card:hover {
            transform: translateY(-5px);
            border-color: #c52d49;
        }
        .management-card .icon {
            font-size: 30px;
            margin-bottom: 15px;
        }
        .management-card h3 {
            margin-bottom: 12px;
            color: #333;
        }
        .management-card p {
            color: #777;
            font-size: 14px;
            line-height: 1.5;
            margin-bottom: 20px;
        }
        .management-card a {
            display: inline-block;
            background: #8f1025;
            color: white;
            text-decoration: none;
            padding: 10px 16px;
            border-radius: 8px;
            font-size: 14px;
            transition: 0.3s;}
        .management-card a:hover {background: #bd2943;}
        .footer {
            text-align: center;
            color: #999;
            font-size: 13px;
            margin-top: 45px;
            padding-bottom: 15px;
        }
        @media (max-width: 1000px) {  .stats {grid-template-columns: repeat(2, 1fr);}
            .management {grid-template-columns: 1fr;}
        }
        @media (max-width: 650px) {
            .sidebar {
                position: relative;
                width: 100%;
                height: auto;
            }
            .logout {
                position: static;
                margin-top: 20px;
            }
            .main {
                margin-left: 0;
                padding: 25px 18px;
            }
            .header {
                align-items: flex-start;
                flex-direction: column;
            }
            .stats {grid-template-columns: 1fr;}
        }
    </style>
</head>
<body>
<aside class="sidebar">
    <div class="logo">♥ Don<span>Action</span></div>
    <h3>Navigation</h3>
    <a href="<%= request.getContextPath() %>/admin">🏠 Tableau de bord</a>
    <a href="<%= request.getContextPath() %>/donneurs">👥 Gestion des donneurs</a>
    <a href="<%= request.getContextPath() %>/collectes">🩸 Gestion des collectes</a>
    <a href="<%= request.getContextPath() %>/rendezvous">📅 Gestion des rendez-vous</a>
    <a class="logout" href="<%= request.getContextPath() %>/deconnexion">↪ Se déconnecter</a>
</aside>
<main class="main">
    <header class="header">
        <div><h1>Tableau de bord</h1>
            <p>Vue générale de la gestion des dons de sang</p>
        </div>
        <div class="admin-badge">Administrateur</div>
    </header>
    <section class="welcome">
        <h2>Bienvenue, <%= utilisateur.getPrenom() %> ! ❤️</h2>
        <p>Ensemble, chaque don compte.
            Gérez les donneurs, les collectes et les rendez-vous
            depuis votre espace d'administration.</p>
    </section>
    <h2 class="section-title">Statistiques générales</h2>
    <section class="stats">
        <div class="stat-card">
            <div class="stat-icon">👥</div>
            <h3>Utilisateurs</h3>
            <div class="number">
                <%= request.getAttribute("nombreUtilisateurs") %>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon">❤️</div>
            <h3>Donneurs</h3>
            <div class="number">
                <%= request.getAttribute("nombreDonneurs") %>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon">🩸</div>
            <h3>Collectes</h3>
            <div class="number">
                <%= request.getAttribute("nombreCollectes") %>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon">📅</div>
            <h3>Rendez-vous</h3>
            <div class="number">
                <%= request.getAttribute("nombreRendezVous") %>
            </div>
        </div>
    </section>

    <h2 class="section-title">État des activités</h2>
    <section class="activity">
        <p>🩸 Collectes planifiées :
            <strong><%= request.getAttribute("collectesPlanifiees") %></strong>
        </p>

        <p>⏳ Rendez-vous en attente :
            <strong><%= request.getAttribute("rendezVousEnAttente") %></strong>
        </p>
        <p>✅ Rendez-vous confirmés :
            <strong><%= request.getAttribute("rendezVousConfirmes") %></strong>
        </p>
    </section>

    <h2 class="section-title">Accès rapide</h2>

    <section class="management">
        <div class="management-card">
            <div class="icon">👥</div>
            <h3>Donneurs</h3>
            <p>Consulter et gérer les informations des donneurs inscrits.</p>
            <a href="<%= request.getContextPath() %>/donneurs">Gérer les donneurs →</a>
        </div>
        <div class="management-card">
            <div class="icon">🩸</div>
            <h3>Collectes</h3>
            <p>Accéder aux campagnes et aux opérations de collecte de sang.</p>
            <a href="<%= request.getContextPath() %>/collectes">Gérer les collectes →</a>
        </div>
        <div class="management-card">
            <div class="icon">📅</div>
            <h3>Rendez-vous</h3>
            <p>Consulter et suivre les rendez-vous des donneurs.</p>
            <a href="<%= request.getContextPath() %>/rendezvous">Gérer les rendez-vous →</a>
        </div>
    </section>

    <footer class="footer">Gestion des dons de sang — Plateforme de solidarité ❤️</footer>
</main>
</body>
</html>