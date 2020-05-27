# Telegram bank bot

Ваше время - это бесценный ресурс и мы его ценим, поэтому для вас доступен бот «bank-help» в telegram. Это ваш помощник, который поможет быстро узнать информацию по вашим картам, получить баланс или выписку по карте и узнать актуальный курс валют. Данные доступны для трех банков ПриватБанк, Монобанк, Укрсиббанк.
## Поиск бота в telegram messenger

•  В мессенджере нажать значок поиска (лупа в правом верхнем углу)  

•  Набрать текст «FinanceManagerBot» в поисковой строке 

•  Запуск взаимодействия с Ботом осуществляется нажатием кнопки START 

•  Или подключите Чат-бот по прямой ссылке https://t.me/financecontrolmanager_bot 

## Разработчики
https://github.com/Ekrosrb/ - телеграм бот

https://github.com/andrevbnk - сервер, работа с бд

https://github.com/alwayswannachange - шаблоны банков


## Server
https://github.com/andrevbnk/telega


## How to use (Client)

1. Подключение к боту и добавление базового функционала.

```java
        String token = ""; //Здесь должен быть токен.
        TelegramBot t = new TelegramBot(token);
        Host host = new Host(""); //url сервера который связывается с api банков.
        t.addEventListener(new DataEvent(t, host));
        t.addEventListener(new RateEvent(t, host));
        t.connect();
```

Класс **TelegramBot** служит для подключения в боту. Достаточно просто указать token и вызвать connect().  
Класс **Host** нужен для связи с сервером который управляет операциями с токеном и взаимодействует с api банков.  
addEventListener(EventListener eventListener) - Добавляет выбраный обработчик событий.   
**DataEvent** - Набор команд для добавления токена и получения информации с сервера.  
**RateEvent** - Набор команд для получения курса валют.  

2. Создание своих обработчиков событий.

```java
public class MyEvent implements EventListener {
    public MyEvent(TelegramBot t, Host host){
        this.t = t;
        this.host = host;
    }
    private Host host;
    private TelegramBot t;
    @Override
    public void onEventListener(IUpdate update) {
      //your code
    }
}
```
При получении обновлений с бота, вызываеться метод onEventListener(IUpdate update).  
Через поле update можно получить всю необходимую информацию, например: update.getMessage().getText() - вернет текс сообщения.

3. Отправка сообщений.

Для отправки сообщений используется метод sendMessage из класса **TelegramBot**.  
```java
public class MyEvent implements EventListener {
    public MyEvent(TelegramBot t, Host host){
        this.t = t;
        this.host = host;
    }
    private Host host;
    private TelegramBot t;
    @Override
    public void onEventListener(IUpdate update) {
        String mess = update.getMessage().getText();
        t.sendMessage(update.getMessage().getChat(), "Your message: " + mess);
    }
}
```
Так же можно отправлять сообщения с пользовательскими кнопками.
```java
        t.sendMessage(update.getMessage().getChat(), "Your message", new String[]{"button1", "button2", "button3"});
```
Названия кнопок передаём массивом строк.
