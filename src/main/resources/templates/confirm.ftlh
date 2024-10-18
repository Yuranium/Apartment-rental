<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Подтверждение регистрации</title>
</head>
<body>
<h1>Подтверждение регистрации</h1>

<form action="/confirm" method="POST">
    <p>Введите код подтверждения:</p>
    <label for="code">Цифра 1</label>
    <input type="number" id="code" name="code" maxlength="1" required>

    <label for="code">Цифра 2</label>
    <input type="number" id="code" name="code" maxlength="1" required>

    <label for="code">Цифра 3</label>
    <input type="number" id="code" name="code" maxlength="1" required>

    <label for="code">Цифра 4</label>
    <input type="number" id="code" name="code" maxlength="1" required>

    <label for="code">Цифра 5</label>
    <input type="number" id="code" name="code" maxlength="1" required>

    <label for="code">Цифра 6</label>
    <input type="number" id="code" name="code" maxlength="1" required>

    <button type="submit">Подтвердить</button>
</form>

<p>Отправить повторно<a href="/confirm?repeat=true">Отправить новый код</a></p>
</body>
</html>
