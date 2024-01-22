## İçerik



Herkese merhaba arkadaşlar.Bugün Jetpack Compose ile Pokemon App geliştirdim.Uygulamada datalar sayfalara göre getirilmektedir.Yani 1 2 3. sayfa şeklinde apiye kaydırdıkça istek atılmaktadır
Bunun kontrolünü scrollun pozisyonu ile elimdeki listenin size karşılaştırarak kontrol ettim.Dark Theme de mevcut.Arama kısımında ise pokemon arayabiliyorsunuz.Sonuç olmayınca bulunamadı gibi 
Kullanıcıya hata mesajları veriliyor.Detay sayfası içerisinde pokemonun hp,atak,damage gibi bilgilerini animasyon kullanarak göstermeye çalıştım.(animateFloatAsState aracılığıyla)
Anasayfadaki pokemonların arka plan renklerini ise CoilImage ile yapmaya çalıştım fakat depcreated olduğu için SubcomposeAsyncImage() diye bir Compose nesnesi kullanarak hallettim.
Bu arka plan renklerini ise pokemonun görsel resimini vererek onun içinde olan renkleri dikkate alarak yeni bir renk oluşturuyor bize.
Detay sayfasına da giderken pokemonun isimini tekrar apiye path olarak istek atıyoruz.




## Kullanılan Teknolojiler : 




-Retrofit



-MVVM



-State




-Hilt




-SubcomposeAsyncImage()





-Timber





-Coroutines




-Navigations,Arguments







## Video


https://github.com/Cntrk01/PokedexAppCompose/assets/98031686/074181d6-5121-47ee-a030-68c9b1fbd3ae





## Çıktılar

![Ekran görüntüsü 2024-01-21 200020](https://github.com/Cntrk01/PokedexAppCompose/assets/98031686/11a3a184-26f2-47c4-a0ad-53d2488b956d)   ![Ekran görüntüsü 2024-01-21 200009](https://github.com/Cntrk01/PokedexAppCompose/assets/98031686/39cc6a2d-b189-428e-9071-ec07b5421333)


![Ekran görüntüsü 2024-01-21 195950](https://github.com/Cntrk01/PokedexAppCompose/assets/98031686/86613191-bbe3-4643-9442-db51d9b75b24)    ![Ekran görüntüsü 2024-01-21 200026](https://github.com/Cntrk01/PokedexAppCompose/assets/98031686/58f22c1b-ba80-4318-ac56-8a518633743a)


