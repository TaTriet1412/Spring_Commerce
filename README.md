# Dự Án Phát Triển Phần Mềm SPRING COMMERCE

## Tổng Quan

Dự án này được xây dựng sử dụng các công nghệ **Docker**, **MySQL**, **Angular**, và **Spring Boot**. Mục tiêu của dự án là minh họa các nguyên tắc phát triển phần mềm hiện đại, mẫu thiết kế và các thực tiễn tốt nhất trong việc xây dựng ứng dụng có thể mở rộng, dễ bảo trì và hiệu quả.

## Nguyên Tắc Phát Triển Phần Mềm

### 1. **Tách Biệt Các Mối Quan Tâm (Separation of Concerns - SoC)**
   - Kiến trúc dự án được thiết kế để đảm bảo các mối quan tâm khác nhau của ứng dụng (ví dụ: giao diện người dùng, logic nghiệp vụ, lưu trữ dữ liệu) được tách biệt thành các lớp riêng biệt.
   - **Angular** chịu trách nhiệm về phần giao diện người dùng (UI) và xử lý tương tác với người dùng thông qua các **API**.
   - **Spring Boot** quản lý logic nghiệp vụ phía server và cung cấp các **RESTful API** để giao tiếp với **Angular**.
   - **MySQL** được sử dụng để lưu trữ dữ liệu, và **Docker** đảm bảo môi trường phát triển và sản xuất đồng nhất.

### 2. **DRY (Don't Repeat Yourself - Đừng Lặp Lại Mình)**
   - Dự án tuân theo nguyên tắc DRY bằng cách đảm bảo mã nguồn tái sử dụng và tránh sự lặp lại không cần thiết.
   - Các lớp dịch vụ trong **Spring Boot** được thiết kế để tái sử dụng nhiều lần, và các component trong **Angular** giúp tái sử dụng mã giao diện người dùng.

### 3. **KISS (Keep It Simple, Stupid - Giữ Đơn Giản)**
   - Dự án này luôn cố gắng giữ cho mã nguồn đơn giản và dễ hiểu, tránh các thiết kế phức tạp không cần thiết.
   - Việc sử dụng **Spring Boot** cho các chức năng back-end giúp giảm thiểu sự phức tạp trong việc quản lý các cấu hình và các thư viện bên ngoài.

### 4. **YAGNI (You Aren't Gonna Need It - Bạn Không Cần Nó)**
   - Trong quá trình phát triển, chỉ những tính năng thực sự cần thiết mới được triển khai. Điều này giúp giảm thiểu độ phức tạp và thời gian phát triển.
   - Chúng tôi tránh việc thêm các tính năng chưa cần thiết vào giai đoạn đầu của dự án.

## Mẫu Thiết Kế (Design Patterns) Áp Dụng

### 1. **RESTful API Architecture**
   - Dự án sử dụng kiến trúc **RESTful API** để xử lý các yêu cầu giữa **Angular** (frontend) và **Spring Boot** (backend). Các endpoint API trong **Spring Boot** nhận và trả dữ liệu (thường là JSON) khi nhận các yêu cầu từ ứng dụng Angular.
   - Các phương thức HTTP như **GET**, **POST**, **PUT**, **DELETE** được sử dụng để thao tác với các tài nguyên.

### 2. **Service Layer Pattern**
   - Dự án sử dụng mẫu thiết kế **Service Layer** để tổ chức mã nguồn một cách rõ ràng và dễ bảo trì. Lớp **Service** trong **Spring Boot** xử lý các nghiệp vụ và logic ứng dụng, tương tác với **Repository** để truy xuất hoặc lưu trữ dữ liệu trong **MySQL**.
   - Các controller trong **Spring Boot** nhận yêu cầu từ **Angular**, gọi đến các service để thực hiện các nghiệp vụ, và trả kết quả dưới dạng phản hồi JSON.

### 3. **Repository Pattern**
   - Mẫu thiết kế **Repository** được sử dụng trong **Spring Boot** để tương tác với cơ sở dữ liệu, giúp tách biệt logic truy cập dữ liệu khỏi các lớp dịch vụ. Repository này giúp dễ dàng thực hiện các thao tác CRUD với **MySQL**.

## Thực Tiễn Phát Triển Phần Mềm

### 1. **DevOps và Continuous Integration**
   - Dự án này áp dụng các thực tiễn DevOps để đảm bảo việc triển khai liên tục và tự động. **Docker** được sử dụng để đóng gói ứng dụng và đảm bảo môi trường phát triển đồng nhất.
   - Các công cụ tích hợp liên tục (CI) như Jenkins hoặc GitLab CI được sử dụng để tự động kiểm tra, xây dựng và triển khai ứng dụng.

### 2. **Testing**
   - **JUnit** và **Mockito** được sử dụng trong **Spring Boot** để thực hiện kiểm thử đơn vị, giúp đảm bảo các chức năng phía server hoạt động chính xác.
   - **Karma** và **Jasmine** được sử dụng trong **Angular** để thực hiện kiểm thử đơn vị cho các component và service, đảm bảo tính ổn định của giao diện người dùng.

### 3. **Containerization và Môi Trường Phát Triển Đồng Nhất**
   - Sử dụng **Docker** để tạo môi trường phát triển và sản xuất đồng nhất, giúp đảm bảo ứng dụng hoạt động giống nhau trên mọi hệ thống. Docker Compose được sử dụng để dễ dàng quản lý các container cho các dịch vụ như **Spring Boot** và **MySQL**.

### 4. **Quản Lý Phiên Bản**
   - **Git** được sử dụng để quản lý mã nguồn, đảm bảo quy trình phát triển tuân thủ các thực tiễn quản lý mã nguồn chuẩn, từ việc tạo nhánh cho tính năng mới đến việc hợp nhất và triển khai.

## Công Nghệ Sử Dụng

- **Docker**: Dùng để container hóa ứng dụng và tạo môi trường phát triển đồng nhất.
- **MySQL**: Cơ sở dữ liệu quan hệ dùng để lưu trữ dữ liệu của ứng dụng.
- **Angular**: Framework JavaScript cho việc phát triển giao diện người dùng và tương tác với **Spring Boot API**.
- **Spring Boot**: Framework Java cho việc phát triển các ứng dụng back-end mạnh mẽ, cung cấp **RESTful API** để Angular gọi và xử lý dữ liệu.

## Cấu trúc thư mục của dự án Spring Boot (Backend)
spring-commerce/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── springcommerce/
│   │   │           ├── controller/         # Chứa các controller xử lý yêu cầu HTTP từ frontend
│   │   │           ├── entity/             # Chứa các lớp mô hình (entities) phản ánh cấu trúc bảng trong cơ sở dữ liệu
│   │   │           ├── repository/         # Chứa các lớp repository tương tác với cơ sở dữ liệu (CRUD)
│   │   │           ├── service/            # Chứa các lớp dịch vụ xử lý logic nghiệp vụ
│   │   │           ├── request/            # Chứa các lớp chuẩn hóa các yêu cầu từ client
│   │   │           ├── errorhandler/       # Chứa các lớp xử lí khi service gửi lỗi của RunTimeException
│   │   │           ├── security/           # Chứa các lớp cấu hình spring security 
│   │   │           ├── model/              # Chứa thông tin về database
│   │   │           └── SpringCommerceApplication.java  # Lớp chính khởi tạo ứng dụng Spring Boot
│   │   └── resources/
│   │       ├── application.yaml      # Các cấu hình cho ứng dụng (kết nối cơ sở dữ liệu, cấu hình server, etc.)
│   │       └── static/                     # Chứa các tệp tài nguyên tĩnh (nếu có)
│   └── test/                               # Chứa các bài kiểm tra (unit tests) cho ứng dụng
│
└── pom.xml                                  # Quản lý các thư viện và cấu hình xây dựng ứng dụng Spring Boot


## Cấu trúc thư mục của dự án Angular (Frontend)

angular-frontend/
│
├── public/
│   └── assets/                            # Chứa các tài nguyên tĩnh như hình ảnh, CSS, fonts
├── src/
│   ├── app/                               # Các component đại diện cho giao diện người dùng (UI)
│   │   ├── core/                          
│   │   │   ├── services/                  # Các service tương tác với API của backend
│   │   │   ├── interceptors/              # Cấu hình HttpInterceptor vào các phương thức khi cần jwt
│   │   │   └── pipes/                     # Các pipes tự tạo nhằm điều chỉnh giá trị (ví dụ: mỗi 3 số có 1 dấu chấm) 
│   │   ├── app.module.ts                  # Cấu hình module chính của Angular
│   │   └── app.component.ts               # Component chính của ứng dụng Angular
│   └── index.html                         # Tệp HTML chính, nơi ứng dụng Angular được "bootstrapped"
│── angular.json                           # Cấu hình dự án Angular
└── proxy.conf.json                        # Cấu hình CORS để gọi được api từ khác đường localhost

## Các Bước Cần Thiết Để Chạy Ứng Dụng Trên Máy Tính Cục Bộ

Để chạy ứng dụng trên máy tính cục bộ, bạn cần thực hiện các bước dưới đây cho cả phần **backend** (Spring Boot) và **frontend** (Angular).

### 1. **Cài Đặt Môi Trường Phát Triển**

Đảm bảo rằng môi trường phát triển của bạn đã cài đặt các công cụ sau:

#### Các Công Cụ Cần Cài Đặt:

- **JDK 11 hoặc cao hơn**: Dự án Spring Boot yêu cầu Java Development Kit (JDK) phiên bản 11 trở lên. Bạn có thể tải JDK từ 
[Oracle]
(https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) 
hoặc 
[OpenJDK]
(https://openjdk.java.net/).
- **Maven**: Dự án sử dụng Maven để quản lý phụ thuộc. Tải và cài đặt Maven từ 
[Maven]
(https://maven.apache.org/).
- **Node.js và npm**: Angular yêu cầu Node.js và npm. Truy cập 
[Node.js]
(https://nodejs.org/) và tải phiên bản LTS.
- **IntelliJ IDEA**: Dự án sử dụng IntelliJ IDEA để phát triển ứng dụng Spring Boot. Tải và cài đặt IntelliJ từ 
[JetBrains]
(https://www.jetbrains.com/idea/).
- **Git**: Cài đặt Git để quản lý mã nguồn. Tải Git từ 
[Git]
(https://git-scm.com/).
- **Docker**: Cài đặt Docker để chạy Server. Tải Docker từ 
[Docker]
(https://www.docker.com/products/docker-desktop/).

### 2. **Chạy Phần Backend (Spring Boot)**

1. Mở **IntelliJ IDEA**.
2. Chọn **Open** và điều hướng đến thư mục chứa mã nguồn của dự án Spring Boot (thư mục `SpringCommerce`).
3. IntelliJ sẽ tự động nhận diện dự án và tải các phụ thuộc
4. Dưa vào file application.yaml để cấu hình mysql tương thích (chạy database trong workbench với tên spring_commerce, file được cung cấp trong folder Model của com.triet.spring_commerce) *Lưu ý username và password là "root"
5. Bấm nút "Start" sau khi cấu hình thành công

### 2. **Chạy Phần Frontend (Angular)**
1. Mở terminal, cd đến thư mục spring_commerce
2. Cài đặt tất cả các phụ thuộc cần thiết cho dự án Angular bằng cách chạy lệnh sau (npm install)
3. Chạy ứng dụng (npm start)

### 3. **Kiểm tra kết nối Backend và Frontend **
1. Truy cập http://localhost:4200 trong trình duyệt. Đây là giao diện người dùng của ứng dụng Angular.
2. Ứng dụng Angular sẽ gửi các yêu cầu HTTP đến http://localhost:8080 (backend) để lấy hoặc gửi dữ liệu.
3. Kiểm tra xem các chức năng trong ứng dụng có hoạt động như mong đợi hay không, ví dụ: đăng nhập, hiển thị danh sách sản phẩm,...

## Ảnh API hoạt động trong file doc đính kèm (API SPRING COMMERCE)