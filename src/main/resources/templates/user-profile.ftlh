<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Аренда недвижимости</title>
</head>
<body>
<div class="container">
    <h1>Ваш профиль</h1>

    <div class="col-md-6">
        <img src="#" alt="Фото апартамента" class="apartment-image">
    </div>
    <div>
        <div class="row mb-4">
            <div class="col-md-6">
                <label for="firstName">Имя:</label>
                <input type="text" id="firstName" name="firstName" value="Иван" required>
            </div>
            <div class="col-md-6">
                <label for="lastName">Фамилия:</label>
                <input type="text" id="lastName" name="lastName" value="Петров" required>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-md-6">
                <label for="email">Адрес электронной почты:</label>
                <input type="email" id="email" name="email" value="ivan.petrov@example.com" required>
            </div>
            <div class="col-md-6">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" value="securePassword123!" required>
            </div>
            <#--            <div class="col-md-6">-->
            <#--                <label for="password">Повторите пароль:</label>-->
            <#--                <input type="password" id="password" name="password" value="securePassword123!" required>-->
            <#--            </div>-->
        </div>

        <div class="row mb-4">
            <div class="col-md-6">
                <label for="birthday">День рождения:</label>
                <input type="date" id="birthday" name="birthday" value="1990-05-25" required>
            </div>
        </div>

        <form action="/user/delete" method="GET">
            <input type="hidden" name="_method" value="DELETE">
            <button type="submit" class="btn btn-primary">Удалить профиль</button>
        </form>
    </div>
</div>
</body>
</html>