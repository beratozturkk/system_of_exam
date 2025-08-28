# Sınav Sistemi (System of Exam)

Bu proje, Java Spring Boot backend ve React frontend kullanılarak geliştirilmiş modern bir sınav sistemidir. Sistem, öğrenci, öğretmen ve admin rollerini destekler.

## 🚀 Özellikler

### Backend (Spring Boot)
- **JWT Authentication**: Güvenli token tabanlı kimlik doğrulama
- **Role-based Access Control**: STUDENT, TEACHER, ADMIN rolleri
- **PostgreSQL Database**: Güvenilir veri saklama
- **RESTful API**: Modern API tasarımı
- **Spring Security**: Güvenlik konfigürasyonu

### Frontend (React)
- **Modern UI/UX**: Responsive ve kullanıcı dostu arayüz
- **Role-based Dashboard**: Her rol için özelleştirilmiş dashboard
- **Real-time Updates**: Gerçek zamanlı veri güncellemeleri
- **Mobile Responsive**: Mobil cihazlarda da kullanılabilir

## 🛠️ Teknolojiler

### Backend
- Java 24
- Spring Boot 3.5.4
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Tokens)
- Maven

### Frontend
- React 18
- TypeScript
- React Router DOM
- CSS3
- Fetch API

## 📋 Gereksinimler

- Java 24+
- Maven 3.6+
- PostgreSQL 12+
- Node.js 16+
- npm 8+

## 🚀 Kurulum

### 1. Backend Kurulumu

```bash
# Projeyi klonlayın
git clone <repository-url>
cd SystemOfExam

# PostgreSQL veritabanını oluşturun
createdb system_of_exam

# application.properties dosyasını düzenleyin
# Veritabanı bağlantı bilgilerini güncelleyin

# Projeyi derleyin ve çalıştırın
mvn clean compile
mvn spring-boot:run
```

Backend varsayılan olarak `http://localhost:8080` adresinde çalışacaktır.

### 2. Frontend Kurulumu

```bash
# Frontend klasörüne gidin
cd frontend

# Bağımlılıkları yükleyin
npm install

# Geliştirme sunucusunu başlatın
npm start
```

Frontend varsayılan olarak `http://localhost:3000` adresinde çalışacaktır.

## 🔧 Konfigürasyon

### Backend Konfigürasyonu

`src/main/resources/application.properties` dosyasında:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/system_of_exam
spring.datasource.username=postgres
spring.datasource.password=12345
```

### Frontend Konfigürasyonu

Backend URL'ini `src/contexts/AuthContext.tsx` dosyasında güncelleyin:

```typescript
const response = await fetch('http://localhost:8080/api/auth/login', {
  // ...
});
```

## 👥 Kullanıcı Rolleri

### STUDENT (Öğrenci)
- Aktif sınavları görüntüleme
- Sınav detaylarını inceleme
- Sınavlara katılma

### TEACHER (Öğretmen)
- Yeni sınav oluşturma
- Mevcut sınavları düzenleme
- Sınav sonuçlarını görüntüleme

### ADMIN (Yönetici)
- Kullanıcı yönetimi
- Sistem ayarları
- Genel istatistikler

## 📚 API Endpoints

### Authentication
- `POST /api/auth/login` - Kullanıcı girişi
- `GET /api/auth/me` - Mevcut kullanıcı bilgileri

### Users
- `POST /api/users/register` - Yeni kullanıcı kaydı

### Exams
- `GET /api/exams` - Tüm sınavları listele
- `GET /api/exams/active` - Aktif sınavları listele
- `GET /api/exams/{id}` - Belirli bir sınavı getir
- `POST /api/exams/create` - Yeni sınav oluştur (TEACHER)

## 🔐 Güvenlik

- JWT token tabanlı kimlik doğrulama
- Role-based access control
- CORS konfigürasyonu
- Şifre hash'leme (BCrypt)

## 🎨 UI/UX Özellikleri

- Modern ve temiz tasarım
- Responsive layout
- Smooth animasyonlar
- Intuitive navigation
- Error handling
- Loading states

## 📱 Responsive Tasarım

Frontend, tüm cihaz boyutlarında optimal çalışacak şekilde tasarlanmıştır:
- Desktop (1200px+)
- Tablet (768px - 1199px)
- Mobile (< 768px)

## 🚀 Geliştirme

### Backend Geliştirme
```bash
# Hot reload ile geliştirme
mvn spring-boot:run

# Test çalıştırma
mvn test
```

### Frontend Geliştirme
```bash
# Geliştirme sunucusu
npm start

# Production build
npm run build

# Test çalıştırma
npm test
```

## 📝 Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/AmazingFeature`)
3. Commit yapın (`git commit -m 'Add some AmazingFeature'`)
4. Push yapın (`git push origin feature/AmazingFeature`)
5. Pull Request oluşturun

## 📞 İletişim

Proje hakkında sorularınız için:
- Email: [your-email@example.com]
- GitHub: [your-github-username]

## 🙏 Teşekkürler

Bu proje aşağıdaki teknolojiler ve topluluklar sayesinde mümkün olmuştur:
- Spring Boot ekibi
- React ekibi
- PostgreSQL ekibi
- Açık kaynak topluluğu 