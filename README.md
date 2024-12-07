# Apartment-rental
Веб-приложение для реализации аренды недвижимости и сдачей в аренду. Приложение имеет веб часть,
а также RESTful часть. Приложение имеет весь CRUD функционал, кеширование данных,
умный поиск и в веб-части автодополнение при поиске с помощью второй базы данных elasticsearch,
описана схема языка запросов `graphQL` для получения необходимых данных,
общение со сторонним сервисом на *Python Django*, также для приложения была написана документация в
спецификации `openAPI`. Также пользователь может непосредственно сохранять ___файлы___ в базу данных в **бинарном** формате,
а при регистрации нового пользователя реализована отправка email сообщения с шестизначным кодом подтверждения
по протоколу SMTP для подтверждения регистрации.


### **Используемые технологии**
![](https://skillicons.dev/icons?i=java,idea,spring,postgres,redis,graphql)

![](https://skillicons.dev/icons?i=python,pycharm,django,docker,rabbitmq,elasticsearch)

![](https://skillicons.dev/icons?i=gmail,postman,bootstrap,html,css,js)
* Java - _язык программирования_
* Spring Boot framework
* PostgreSQL - _основная SQL база данных_
* Redis - _прослойка для кеширования данных_
* GraphQL - _язык запросов_
* Python - _язык программирования_
* Django framework
* Docker - _контейнеризация приложения_
* RabbitMQ - _брокер сообщений_
* OpenAPI - _спецификация документации API приложения_
* Elasticsearch - _поисковой движок и документоориентированная база данных_
* Gmail sender application - _реализация отправки сообщений по почте_
* Bootstrap - _подключение шаблонов HTML_

### Запуск

1. Для начала нужно скачать все образы из `docker-compose` файла командой: `docker compose up`, команда
автоматически скачает нужные образы и запустит систему.
2. Создать базу данных в PostgreSQL.
3. Перейти по URL адресу: `localhost:5601` в веб-приложение Kibana, в разделе **developer tools** создать
индекс, который описан в файле `resources/static/elasticsearch/elastic.json` проекта.
4. Запустить приложения **Python Django** для отображения графика платежей (_Опционально_)
5. Запустить приложение на **java**.

### Демо

1) #### Форма регистрации в браузере
![registration.png](github%2Fregistration.png)

2) #### Форма логина в браузере
![login.png](github%2Flogin.png)

3) #### Пример кода подтверждения, приходящего по почте
![email-confirm-code.png](github%2Femail-confirm-code.png)

4) #### Профиль пользователя с графиком его платежей
![user-profile.png](github%2Fuser-profile.png)

5) #### Профиль апартамента с добавленными картинками в БД и доп. сервисами
![apartment-profile.png](github%2Fapartment-profile.png)

6) #### Панель админа для редактирования пользователей
![admin-panel.png](github%2Fadmin-panel.png)

7) #### Работа через REST в Postman
![payment-rest.png](github%2Fpayment-rest.png)

8) #### Преобразованная фотография из базы данных
![image-rest.png](github%2Fimage-rest.png)

9) #### Документация проекта в спецификации openAPI
![swagger-main.png](github%2Fswagger-main.png)

10) #### Описание отдельного эндпоинта приложения
![swagger-endpoint.png](github%2Fswagger-endpoint.png)