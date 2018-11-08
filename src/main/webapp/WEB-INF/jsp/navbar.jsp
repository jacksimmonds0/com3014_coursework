<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">Group 6 Movies</a>
        </div>

        <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#">Movies</a></li>
            <li><a href="#">Nearest Cinema</a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <form id="search-form" class="navbar-form navbar-left" method="get" action="" onsubmit="search();">
                <div class="form-group">
                    <input type="text" class="form-control" id="search-box" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-primary">
                    <span class="glyphicon glyphicon-search"></span>
                </button>
            </form>
            <li><a href="register"><span class="glyphicon glyphicon-user"></span> Register</a></li>
            <li><a href="login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
        </ul>
    </div>
</nav>