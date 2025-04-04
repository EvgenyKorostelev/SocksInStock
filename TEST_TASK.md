# Задача:
## **Создать REST API для учета носков на складе магазина.**

### **Функционал**

#### **Регистрация прихода носков:**
POST /api/socks/income
Параметры: цвет носков, процентное содержание хлопка, количество.
Увеличивает количество носков на складе.


#### **Регистрация отпуска носков:**
POST /api/socks/outcome
Параметры: цвет носков, процентное содержание хлопка, количество.
Уменьшает количество носков на складе, если их хватает.


#### **Получение общего количества носков с фильтрацией:**
GET /api/socks
Фильтры:
Цвет носков.
Оператор сравнения (moreThan, lessThan, equal).
Процент содержания хлопка.
Возвращает количество носков, соответствующих критериям.


#### **Обновление данных носков:**
PUT /api/socks/{id}.
Позволяет изменить параметры носков (цвет, процент хлопка, количество).


#### **Загрузка партий носков из Excel или CSV (один формат на выбор) файла:**
POST /api/socks/batch
Принимает Excel или CSV (один формат на выбор) файл с партиями носков, содержащими цвет, процентное содержание хлопка и количество.



### **Дополнительные требования**

#### **Логирование:**
Реализовать логирование всех операций (приход, отпуск, обновление, запросы) с использованием SLF4J.


#### **Дополнительные критерии поиска:**
Возможность фильтрации по диапазону процентного содержания хлопка (например, от 30 до 70%).
Возможность сортировки результата по цвету или проценту хлопка.


#### **Улучшенная обработка ошибок:**
Реализовать централизованную обработку ошибок с использованием @ControllerAdvice.
Возвращать понятные сообщения об ошибках:
Некорректный формат данных.
Нехватка носков на складе.
Ошибки при обработке файлов.


#### **Документация:**
Использовать Swagger/OpenAPI для автоматической генерации документации.



### **Требования:**
Язык реализации: Java 17.
Фреймворк: Spring Boot 2.7.
База данных:  PostgreSQL.
версия JDK : 17
Система сборки: Gradle
Формат файла для загрузки партий: Excel (.xlsx) или .csv  (один формат на выбор)
Тестирование: Покрытие тестами минимум 50% кода.