# VibeProject
There's the first aim of this project create a telestrator android app prototype.

---------------

###In-Progress
    [-] Create Local Database to store coordinates as csv, json and xml.
    [-] ListView update process.
    [-] Add butterknife to all activity class. [Priority = 3]

###TODO
    [-] Video uzantılarını kontrol edicek bir string parser method yazılacak. [03.20.15]
    [-] Playback implement edilecek.
        [-] Belirli saniyelerde draw etme işlemi. Bitmap vs.
    [-] Chart library test ve implementasyon detayları.
    [-] Real-time senkronizasyon için başta Firebase kullanılacak.
    [-] When user start to play video on internet, you should wait for a while, using spinner.

###Tasarım TODO-List


###BUG-List
    [-] There is a bug when user change the screen orientation portrait to landscape.
    [+] When user press the back button playing the video,
    [-] Dosya formatı kontrolü yok, farklı tipte bir dosya seçilirse patlıyor.

    
    
----------------------
###Done-List

    [+] Yeni bir CustomView class oluşturulacak, View dan türeyecek.
        [+] CustomView test işlemi ve entegrasyonu.( Bu çözüm yolunun uygun olmadığı tespit edilmiştir.)
        [+] Transparent layout oluşturularak, VideoView üzerine eklenecektir.
            [+] Transparent Layout, Manifest ekleme işlemi yapılacak.

    [+] Video dolarken güzel animasyonlu spinner ekle. [02.23.15]
    [+] Graph chart library eklendi. [02.23.15]
    [+] Sweet Alert Dialog library eklendi. [02.23.15]
    [+] Yeni tasarım button eklenecek. [02.28.15]
    [+] Chart library entegrasyonu.
-----------------------

    [+] Video information ekranı tasarlandı, [03.06.15]
        [+]video bilgileri çekilmesi için araştırma yapıldı.
    [+] Firebase için küçük bir test uygulaması yazıldı. [03.10.15]
    [+] Backward and Forward button implemented.
    [+] Capture button works. [03.18.15]
    [+] Seekbar implement edilecek.[03.23.15]
-----------------------

    [+] Take a partial screenshot and save image to sdcard. [04.05.15]
        [-] There is a bug which sometimes don't save to sdcard. [04.24.15]
        
-----------------------

##Notation Rules

###Kısaltmalar
 * View Widget id isimleri, önce değişken ismi sonra widget kısaltması şeklinde,
     Örnek, "variableName_bt", "variableName_tv"
        * bt --> button, tv --> textview

 * XML file içersinde, önce attribute_name_, sonra widget kısaltması.
        Örnek, *<dimen name="padding_size_bt">24dp</dimen>

 * Private memberlar, önce member olduğunu belirtmek için m, sonra değişken ismi, en son
        widget kısaltması şeklindedir.
        Örnek, "mFileChooser_bt", "mTitle_et"


###Dökümantasyon ve Notlar

1. **Not:** __extends__ **View** şeklinde oluşturulan class lar, **XML** dosyasında, package name ile gösterilebilmektedir. Buradan **android:width** veya **android:height** ayarlayabiliriz.
2. **Not:__(02.16.2015)__** VideoView'u inherit eden bir widget üzerinde, View tarafından sağlanan, onDraw vesaire methodları ile Video üzerine çizim yapılamamakta. Şuan için geçici çözüm, VideoView üzerine Transparent olarak Layout koyup üstüne çizim işlemlerini gerçekleştirmek.
3. **Not:__(02.21.2015)__** Yeni intent çağrıldığında, ana process devam ediyo işlemlerine View üzerinde değişiklik yapıcaksan, kesinlikle **onActivityResult** ile yapabiliyorsun.
