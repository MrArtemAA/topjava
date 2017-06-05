<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
        <span class="glyphicon glyphicon-text-background"></span>
        ${pageContext.response.locale} <span class="caret"></span></a>
    <ul class="dropdown-menu">
        <li><a onclick="changeLocale('en')">EN</a></li>
        <li><a onclick="changeLocale('ru')">RU</a></li>
    </ul>
</li>
<script type="text/javascript">
    //var localeCode="ru";
    function changeLocale(locale) {
        window.location.href = window.location.href.split('?')[0] + '?locale=' + locale;
    }
</script>
