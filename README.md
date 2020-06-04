# eRekrutacja

[![Maven badge](https://img.shields.io/badge/Maven-4.0.0-red)](https://maven.apache.org)
[![Lombok badge](https://img.shields.io/badge/Project_Lombok-1.18.12-green)](https://mvnrepository.com/artifact/org.projectlombok/lombok)
[![Hibernate badge](https://img.shields.io/badge/Hibernate-5.4.13-yellow)](https://mvnrepository.com/artifact/org.hibernate/hibernate-core)
[![PostgreSql badge](https://img.shields.io/badge/PostgreSQL-42.2.12-%2346A9EE)](https://mvnrepository.com/artifact/org.postgresql/postgresql)
[![Spark badge](https://img.shields.io/badge/Spark-2.5.4-blueviolet)](https://mvnrepository.com/artifact/com.sparkjava/spark-core/2.5.4)
[![Gson badge](https://img.shields.io/badge/Gson-2.8.0-yellowgreen)](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.0)

## Krótki opis
Backendowy komponent obsługujący bazę danych do zarządzania procesem e-rekrutacji na studia I stopnia.

## Diagram bazy danych
![diagram](https://github.com/szarbartosz/eRecruitment/blob/master/diagram.png)

## Struktura projektu
```
  .
  ├── controller                              # Klasy odpowiadające za REST API
  │   ├── AuthenticationController.java
  │   ├── config                              # Konfiguracja zwracanych JSONów
  │   │   ├── StandardResponse.java
  │   │   └── Status.java
  │   ├── Controller.java
  │   ├── ExamController.java
  │   ├── FacultyController.java
  │   ├── FieldController.java
  │   ├── RecruitmentController.java
  │   └── StudentController.java
  ├── dao                                     # Metody odpowiedzialne za komunikację z bazą danych
  │   ├── SessionFactoryDecorator.java        
  │   ├── StudentDao.java                     # Metody po stronie aplikanta
  │   └── UniversityDao.java                  # Metody po stronie uczelni
  ├── Main.java
  └── model                                   # Model danych
      ├── Address.java
      ├── Candidate.java
      ├── Exam.java
      ├── Faculty.java
      ├── Field.java
      └── Student.java
```
## Opis funkcjonalności

Projekt realizuje jednoetapową rekrutację studentów na wybrane kierunki studiów I stopnia. Podczas rekrutacji pod uwagę brane są wyniki egzaminów maturalnych, które uprzednio student wprowadza do systemu. Zarejestrowani studenci mogą aplikować na różne kierunki. Uczelnia, jako główny zarządca systemu, manipuluje dostępnymi wydziałami i kierunkami studiów.

## Obsługiwane endpointy (../java/main)
Wykorzystany przez nas framework Spark domyślnie uruchamia serwer na porcie 4567.

### get
```java
get("/students", StudentController.getAllStudents);           //pobranie listy zarejestrowanych aplikantóœ
get("/authenticate", AuthenticationController.authenticate);  //autentykacja aplikanta
get("/faculties", FacultyController.getAllFaculties);         //pobranie listy wydziałów
get("/fields", FieldController.getAllFields);                 //pobranie listy kierunków studiów
```


### post
```java
post("/register", AuthenticationController.register);     //rejestracja aplikanta
post("/exams", ExamController.addExam);                   //przypisanie wyników egzaminu do aplikanta
post("/candidacies", StudentController.apply);            //aplikacja na dany kierunek studiów
post("/faculties", FacultyController.addFaculty);         //dodanie nowego wydziału
post("/fields", FieldController.addField);                //dodanie nowego kierunku studiów
post("/fields/:id", FieldController.addSubject);          //dodanie nowego przedmiotu branego pod uwagę podczas rekrutacji
```


### patch
```java
patch("/candidacies/:id", RecruitmentController.startRecruitment); //kwalifikacja aplkantów z najlepszymi wynikami egzaminów 
```

Wszystkie endpointy są obsługiwane przez metody zaimplementowane w folderze ocntroller  (../java/controller/)

#### Przykładowa obsługa rządania GET zwracająca wszystkich zarejestrowanych w bazie aplikantów (../java/controller/StudentController)

```java
public static Route getAllStudents = (request, response) -> {
    response.type("application/json");
    Collection<Student> collection;
    try {
        collection = studentDao.getAllStudents();
    } catch (Exception e){
        return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
    }
    return new Gson().toJson(
            new StandardResponse(Status.SUCCESS, "ok", new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation().create().toJsonTree(collection))
    );
};
```


### Strona studenta - ciekawsze funkcjonalności (../java/dao/StudentDao)
#### Rejestracja studenta w bazie danych: 

```java
public void addStudent(String firstName, String secondName, String pesel, String email,
                       String street, String buildingNumber, String zipCode, String city, String hashCode) throws Exception {

    if (!this.emailPattern.matcher(email).matches()){
        throw new Exception("Incorrect email address");
    }

    if (pesel.length() != 11){
        throw new Exception("Incorrect pesel");
    }

    Student student = new Student(firstName, secondName, pesel, email, street, buildingNumber, zipCode, city, hashCode);
    Session session = SessionFactoryDecorator.openSession();
    Transaction transaction = session.beginTransaction();
    session.save(student);
    transaction.commit();
    session.close();
}
```    

#### Wprowadzenie wyników egzaminu wstępnego:

```java
public void addExam(int studentId, String subject, double result) throws Exception {
    if (result < 0.0 || result > 1.0){
        throw new Exception("Incorrect exam result");
    }

    Session session = SessionFactoryDecorator.openSession();
    TypedQuery<Student> query = session.createQuery("SELECT S From Student S WHERE S.studentId = :studentId", Student.class );
    query.setParameter("studentId", studentId);
    Student student = query.getSingleResult();
    Exam exam = new Exam(subject, result);
    student.addExam(exam);
    Transaction transaction = session.beginTransaction();
    session.save(exam);
    session.update(student);
    transaction.commit();
    session.close();
}
```

### Strona uczelni - ciekawsze funkcjonalności  (../java/dao/UniversityDao)
#### Rejestracja nowego wydziału:

```java
public void addFaculty(String name) {
    Faculty faculty = new Faculty(name);
    Session session = SessionFactoryDecorator.openSession();
    Transaction transaction = session.beginTransaction();
    session.save(faculty);
    transaction.commit();
    session.close();
}
```

#### Rejestracja nowego kierunku studiów:

```java
public void addField(int facultyId, String name, int capacity) throws Exception {
    if(capacity <= 0) {
        throw new Exception("Incorrect capacity");
    }
    Session session = SessionFactoryDecorator.openSession();
    TypedQuery<Faculty> query = session.createQuery("SELECT F FROM Faculty F WHERE F.facultyId = :facultyId", Faculty.class);
    query.setParameter("facultyId", facultyId);
    Faculty faculty = query.getSingleResult();
    Field field = new Field(name, capacity);
    faculty.addField(field);
    Transaction transaction = session.beginTransaction();
    session.save(field);
    transaction.commit();
    session.close();
}
```

### Wyjaśnienie:
- wykonujemy zapytanie aby uzyskać dostęp do obiektu faculty o zadanym facultyId w celu sprawdzenia czy dany wydział istnieje oraz aby móc wykonać mapowanie
- następnie tworzymy nowy obiekt field zgodnie z zadanymi parametrami
- w kolejnym etapie wykonanujemy funkcję addField znajdującą się w klasie Faculty, która dodaje obiekt field do setu kierunków studiów znajdującego się w obiekcie faculty oraz polu faculty znajdującemu się w obiekcie field przypisuje referencję do obiektu faculty 
- na koniec stworzone obiekty są mapowane na relacje w bazie danych

```java
// set kierunków studiów oraz metoda addField w klasie Faculty:
@OneToMany(mappedBy = "faculty")
private Set<Field> fields = new LinkedHashSet<>();
    
public void addField(Field field){
    field.setFaculty(this);
    this.fields.add(field);
}
```

```java
// pole faculty w klsaie Field
@Expose
@ManyToOne
@JoinColumn(name="FACULTY_FK")
private Faculty faculty;
```


#### Przypisanie przedmiotu branego pod uwagę podczas rekrutacji do danego wydziału:

```java
public void addMainSubjectToField(int fieldId, String subjectName) {
    Session session = SessionFactoryDecorator.openSession();
    TypedQuery<Field> query = session.createQuery("SELECT F FROM Field F WHERE F.fieldId = :fieldId", Field.class);
    query.setParameter("fieldId", fieldId);
    Field field = query.getSingleResult();
    field.addMainSubject(subjectName);
    Transaction transaction = session.beginTransaction();
    session.update(field);
    transaction.commit();
    session.close();
}
```

## Uruchomienie projektu :elephant:

Aby uruchomić projekt wystarczy odpowiednio skonfigurować połączenie ze stworzoną uprzenio bazą danych w pliku hibernate.cfg.xml. Przykładowa konfiguracja połączenia z bazą danych o nazwie DB dostępną pod adresem http://localhost:5432/DB

```XML
<property name="hibernate.connection.driver_class">org.postgresql.Driver</property>
<property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/DB</property>
<property name="hibernate.connection.username">postgres</property>
<property name="hibernate.connection.password">password</property>
``` 



## Kontrybutorzy :poland: :onion:
<table>
  <tr>
    <td align="center"><a href="https://github.com/szarbartosz"><img src="https://avatars3.githubusercontent.com/u/48298481?s=400&u=f61ccb0f734a51dc2a1115e6478839be62cb2216&v=4" width="100px;" alt=""/><br /><sub><b>Bartosz Szar</b></sub></a><br /></td>
    <td align="cefix fixanter"><a href="https://github.com/kraleppa"><img src="https://avatars1.githubusercontent.com/u/56135216?s=460&u=359e017d16c70a31d3bdb086172308cc6f045acf&v=4" width="100px;" alt=""/><br /><sub><b>Krzysztof Nalepa</b></sub></a><br />
    </td>
  </tr>
</table>


