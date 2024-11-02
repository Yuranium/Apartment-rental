<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Аренда недвижимости</title>
</head>
<body>
<h1>RENTAP</h1><hr>
<span><a href="/registration">Зарегистрироваться</a></span>
<span><a href="/user/login">Войти</a></span>
<h2>Привет, пользователь</h2><br>
<h2>Найти апартаменты сейчас</h2>
<hr>
<form action="/apartments/search" method="POST">
    <h2>Аренда недвижимости</h2>

    <div class="form-group">
        <label for="name">Название</label>
        <input type="text" id="name" name="name">
    </div>

    <div class="form-group">
        <label for="countRooms">Количество комнат:</label>
        <select id="countRooms" name="countRooms">
            <option value="">Выберите количество комнат</option>
            <option value="1">1 комната</option>
            <option value="2">2 комнаты</option>
            <option value="3">3 комнаты</option>
            <option value="4">4 комнаты</option>
            <option value="5">5 и более комнат</option>
        </select>
    </div>

    <div class="form-group">
        <label for="dailyPrice">Цена за день: (Верхний интервал)</label>
        <input type="number" id="dailyPrice" name="dailyPrice" min="0" step="0.01">
    </div>

    <div class="form-group">
        <label for="apartmentType">Тип недвижимости:</label>
        <select id="apartmentType" name="apartmentType">
            <option value="">Выберите тип</option>
            <option value="flat">Квартира</option>
            <option value="hotel">Отель</option>
            <option value="house">Дом</option>
            <option value="office">Офис</option>
        </select>
    </div>

    <button type="submit" class="btn btn-primary">Найти</button>
</form>
<hr>
</body>
</html>