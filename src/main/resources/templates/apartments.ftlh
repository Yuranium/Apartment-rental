<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Аренда недвижимости</title>
</head>
<body>
<h1>RENTAP</h1><hr>
<form action="/apartments/sort" method="POST">
    <input type="checkbox" name="addressSort" id="addressSort"><label for="addressSort">Сортировка по площади</label><br>
    <input type="checkbox" name="roomsSort" id="roomsSort"><label for="roomsSort">Сортировка по количеству комнат</label><br>
    <input type="checkbox" name="priceSort" id="priceSort"><label for="priceSort">Сортировка по цене</label><br>
    <input type="submit" value="Применить">
</form>
<div class="apartment">
    <img src="#" alt="Фотография апартаментов">
    <span>Площадь</span>
    <span>Количество комнат</span>
    <span>Суточная цена аренды</span>
    <span>Номер телефона</span>
</div>
</body>
</html>