<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Аренда недвижимости</title>
</head>
<body>
<h2>Регистрация пользователя</h2>
<form action="/registration" method="POST">

    <label for="name">Имя:</label>
    <input type="text" id="name" name="name" value="${userForm.name!}" required>
<#--    <#if errors?? && errors?has_content && errors.hasFieldErrors("name")>-->
<#--        <div style="color:red;">${errors.getFieldError('name').defaultMessage}</div>-->
<#--    </#if>-->

    <label for="lastName">Фамилия:</label>
    <input type="text" id="lastName" name="lastName" value="${userForm.lastName!}" required>
<#--    <#if errors?? && errors?has_content && errors.hasFieldErrors("lastName")>-->
<#--        <div style="color:red;">${errors.getFieldError("lastName").defaultMessage}</div>-->
<#--    </#if>-->

    <label for="email">Адрес электронной почты:</label>
    <input type="email" id="email" name="email" value="${userForm.email!}" required>
<#--    <#if errors?? && errors?has_content && errors.hasFieldErrors("email")>-->
<#--        <div style="color:red;">${errors.getFieldError("email").defaultMessage}</div>-->
<#--    </#if>-->

    <label for="phone">Телефон:</label>
    <input type="tel" id="phone" name="phone" value="${userForm.phone!}">
<#--    <#if errors?? && errors?has_content && errors.hasFieldErrors("phone")>-->
<#--        <div style="color:red;">${errors.getFieldError("phone").defaultMessage}</div>-->
<#--    </#if>-->

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
<#--    <#if errors?? && errors?has_content && errors.hasFieldErrors("password")>-->
<#--        <div style="color:red;">${errors.getFieldError("password").defaultMessage}</div>-->
<#--    </#if>-->

    <label for="replayPassword">Повторите пароль:</label>
    <input type="password" id="replayPassword" name="replayPassword" required>
<#--    <#if errors?? && errors?has_content && errors.hasFieldErrors("replayPassword")>-->
<#--        <div style="color:red;">${errors.getFieldError("replayPassword").defaultMessage}</div>-->
<#--    </#if>-->

    <label for="birthday">Дата рождения:</label>
    <input type="date" id="birthday" name="birthday">
    <#--    <#if errors?has_content && errors.hasFieldErrors("birthday")>-->
    <#--        <div style="color:red;">${errors.getFieldError("birthday").defaultMessage}</div>-->
    <#--    </#if>-->

    <button type="submit">Зарегистрироваться</button>
</form>
</body>
</html>