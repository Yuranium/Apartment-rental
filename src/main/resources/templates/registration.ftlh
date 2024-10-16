<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Аренда недвижимости</title>
</head>
<body>
<h2>Регистрация пользователя</h2>
<form action="/registration" method="POST">
    <label for="name">Имя:</label>
    <input type="text" id="name" name="name" required>

    <label for="lastName">Фамилия:</label>
    <input type="text" id="lastName" name="lastName" required>

    <label for="email">Адрес электронной почты:</label>
    <input type="email" id="email" name="email" required>

    <label for="phone">Телефон:</label>
    <input type="tel" id="phone" name="phone">

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>

    <label for="replayPassword">Повторите пароль:</label>
    <input type="password" id="replayPassword" name="replayPassword" required>

<#--    <#if (errors?has_content && errors["confirmPassword"])??>-->
<#--        <div style="color: red;">${errors["confirmPassword"]!}</div>-->
<#--    </#if>-->

    <label for="birthday">Дата рождения:</label>
    <input type="date" id="birthday" name="birthday">

    <button type="submit">Зарегистрироваться</button>
</form>
</body>
</html>