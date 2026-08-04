# Uçuş Arama Servisi (Case Study)

## Proje Hakkında

Bu proje, iki farklı SOAP servisinden uçuş bilgilerini alarak istemcilere REST API üzerinden sunan bir uçuş arama uygulamasıdır.

Proje kapsamında;

- İki farklı SOAP servisinin tüketilmesi
- Uçuş bilgilerinin birleştirilmesi
- Aynı uçuşlar arasından en ucuzunun seçilmesi
- Tüm istek ve cevapların PostgreSQL veritabanına loglanması

işlemleri gerçekleştirilmiştir.


---

# Proje Yapısı

```
flight-search-case
│
├── flight-provider-a-soap
├── flight-provider-b-soap
└── flight-search-rest
```

---

# Kullanılan Teknolojiler

- Java 17
- Spring Boot
- Spring Web
- Spring Web Services (SOAP)
- Spring Data JPA
- PostgreSQL
- JAXB
- Maven
- JUnit 5
- Mockito

---

# Mimari

![Mimari](flights.drawio.png)


---

# Modüller

## flight-provider-a-soap

8080 portunda çalışan SOAP servisidir.

---

## flight-provider-b-soap

8081 portunda çalışan SOAP servisidir.

---

## flight-search-rest

8082 portunda çalışan REST uygulamasıdır.

Görevleri:

- İki SOAP servisini tüketmek
- Uçuşları birleştirmek
- En ucuz uçuşları belirlemek
- PostgreSQL'e log kaydetmek

---

# REST Endpointleri

## Tüm uçuşları getir

```
POST /api/flights/search
```

İki SOAP servisinden dönen tüm uçuşları birleştirerek döndürür.

---

## En ucuz uçuşları getir

```
POST /api/flights/cheapest
```

Aşağıdaki alanlara göre aynı olan uçuşları gruplar:

- Uçuş Numarası
- Kalkış Noktası
- Varış Noktası
- Kalkış Tarihi
- Varış Tarihi

Her grup için en düşük fiyatlı uçuş döndürülür.

---

# Örnek İstek

```json
{
  "origin": "IST",
  "destination": "COV",
  "departureDate": "2026-09-10T09:00:00"
}
```

---

# Loglama

Her REST isteği sonrasında aşağıdaki bilgiler PostgreSQL veritabanına kaydedilmektedir.

- Endpoint
- Request (JSON)
- Response (JSON)
- Oluşturulma Tarihi

---

# Projeyi Çalıştırma

Projeler aşağıdaki sırayla çalıştırılmalıdır.

1. flight-provider-a-soap
2. flight-provider-b-soap
3. flight-search-rest

SOAP Servisleri

```
http://localhost:8080/ws
http://localhost:8081/ws
```

REST Servisi

```
http://localhost:8082
```

---

# Test

`FlightSearchServiceTest` sınıfında Mockito kullanılarak en ucuz uçuş algoritması test edilmiştir.