# Telegram bank bot

Бот получает информацию из разных банков по токену клиента, а именно счета, депозиты, кредиты, пополнения, курс валют и т.д.
Будет добавлена возможность оповещения пользователя, например при зачислении денег на счет.
**Внимание!** Это тестовая программа (макет), вместо реальных банков будут использоваться шаблоны написанные вручную.

## Разработчики
https://github.com/Ekrosrb/ - телеграм бот

https://github.com/andrevbnk - сервер, работа с бд

https://github.com/alwayswannachange - шаблоны банков

## How to use

1. Подключение к боту и добавление базового функционала.

```Java
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
**DataEvent** - Набор команд для добавления токена и получения информации с сервером.  
**RateEvent** - Набор команд для получения курса валют.  

2. Создание своих обработчиков событий.

```Java
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
