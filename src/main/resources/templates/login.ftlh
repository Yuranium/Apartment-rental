<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Аренда недвижимости</title>
</head>
<body>
<form action="/login" method="POST">
    <h2>Логин</h2>

    <label for="email">Электронная почта:</label>
    <input type="email" id="email" name="email" required>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>

    <button type="submit">Войти</button>
</form>

<div>
    <span>Нет аккаунта?</span>
    <a href="/registration">Зарегистрироваться!</a>
</div>

</body>
</html>