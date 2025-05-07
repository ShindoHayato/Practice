# SQL

SQL (Structured Query Language) は、リレーショナルデータベース(RDB)を操作するための言語

RDBの種類としては、MySQL、PostgreSQL、Oracle、SQL Serverなどがある
MySQL, PostgreSQLはフリーのオープンソースデータベース、Oracleは商用のデータベース

下記のように、表形式(テーブル)で管理され、行(レコード)と列(カラム)から構成される

| id | name | age |
|----|------|-----|
| 1  | John | 25  |
| 2  | Jane | 30  |
| 3  | Bob  | 22  |
| 4  | Alice| 28  |

RDBを利用しないシステム開発はほぼないため、SQLは必須のスキル

※ データベースはRDBの他にNoSQL(mongoDB, Cassandra, Redis, DynamoDBなど)といわれるキーバリュー型のデータベースもある

## SQLの基本

**用語**
- CRUD(クラッド)
  - Create: Insert
  - Read: Select
  - Update: Update
  - Delete: Delete
- DDL(Data Definition Language)
  - CREATE TABLEのようなテーブル作成、ALTER TABLEのようなテーブル変更、DROP TABLEのようなテーブル削除などデータベースの定義操作
- DML(Data Manipulation Language)
  - CRUD操作
- DCL(Data Control Language)
  - GRANTやREVOKEのような権限操作


| id | name | age |
|----|------|-----|
| 1  | John | 25  |
| 2  | Jane | 30  |
| 3  | Bob  | 22  |
| 4  | Alice| 28  |

```
CREATE TABLE users (
  id INT PRIMARY KEY,
  name VARCHAR(255),
  age INT
);

ALTER TABLE users MODIFY name VARCHAR(255) NOT NULL;

INSERT INTO users (id, name, age) VALUES
(1, 'John', 25),
(2, 'Jane', 30),
(3, 'Bob', 22),
(4, 'Alice', 28);

SELECT id, name, age FROM users;
SELECT id, name, age FROM users WHERE age > 25;

INSERT INTO users (name, age) SELECT name, age FROM users WHERE id IS NOT NULL;

UPDATE users SET age = 26 WHERE id = 1;

DELETE FROM users WHERE id = 2;

SHOW COLUMNS FROM users;
```

SELECT
- SELECT カラム名1, ... FROM テーブル名;
- SELECT カラム名1, ... FROM テーブル名 WHERE 条件;

※ 全てのカラムを取得する場合は`*`を使用することができるが、現場では`*`は基本使わずカラム名を指定すること  
理由：保守性の観点から、カラムが変更された場合に影響が出るため

INSERT
- INSERT INTO テーブル名 (カラム名1, カラム名2, ...) VALUES (値1, 値2, ...);
- INSERT INTO テーブル名 (カラム名1, カラム名2, ...) VALUES (値1, 値2, ...), (値11, 値21, ...), ...;
  - 複数行のINSERTも可能
- INSERT INTO テーブル名 (カラム名1, カラム名2, ...) SELECT カラム名1, カラム名2, ... FROM テーブル名 WHERE 条件;
  - SELECT文を使ってINSERTすることもできる
UPDATE
- UPDATE テーブル名 SET カラム名1 = 値1, カラム名2 = 値2 WHERE 条件;
  - WHEREがない場合、全てのレコードが更新される 
DELETE
- DELETE FROM テーブル名 WHERE 条件;
  - WHEREがない場合、全てのレコードが削除される
  - TRUNCATE TABLE テーブル名; で全てのレコードを削除することもできる


CREATE TABLE
- CREATE TABLE テーブル名 (カラム名1 データ型 [制約], カラム名2 データ型 [制約], ...);
ALTER TABLE
- ALTER TABLE テーブル名 ADD カラム名 データ型 [制約];
- ALTER TABLE テーブル名 MODIFY カラム名 データ型 [制約];
- ALTER TABLE テーブル名 DROP カラム名;
DROP TABLE
- DROP TABLE テーブル名;

## 条件指定

usersテーブル

| id | name | age |
|----|------|-----|
| 1  | John | 25  |
| 2  | Jane | 30  |
| 3  | Bob  | 22  |
| 4  | Alice| 28  |

- SELECT id, name, age FROM users WHERE age = 25;
  - `=`は等しい
- SELECT id, n/ame, age FROM users WHERE age <> 25;
  - `<>`は等しくない
- SELECT id, name, age FROM users WHERE age > 25;
  - `>`はより大きい。 `<`, `>=`, `<=`も同様
- SELECT id, name, age FROM users WHERE age BETWEEN 20 AND 30;
  - `BETWEEN`は範囲指定
- SELECT id, name, age FROM users WHERE age IN (20, 25, 30);
  - `IN`は指定した値の中に含まれる
  - `NOT IN`は指定した値の中に含まれない
- SELECT id, name, age FROM users WHERE name LIKE 'J%';
  - `LIKE`は部分一致
  - `%`は任意の文字列を表す
  - `_`は任意の1文字を表す
- SELECT id, name, age FROM users WHERE name IS NULL;
  - `IS NULL`はNULL値を指定
  - `IS NOT NULL`はNULL値でないことを指定
- SELECT id, name, age FROM users WHERE age = 25 AND name = 'John';
  - `AND`はAND条件
- SELECT id, name, age FROM users WHERE age = 25 OR name = 'Jane';
  - `OR`はOR条件

## ソート
- SELECT id, name, age FROM users ORDER BY age;
- SELECT id, name, age FROM users ORDER BY age DESC, name ASC;
  - `ORDER BY`はソート
  - `ASC`は昇順、`DESC`は降順
  - ORDER BYを指定しない場合、取得結果の順序は保証されない


## 集約関数
- SELECT COUNT(*) FROM users;
  - `COUNT`は件数を取得
- SELECT SUM(age) FROM users;
  - `SUM`は合計を取得
- SELECT AVG(age) FROM users;
  - `AVG`は平均を取得
- SELECT MIN(age) FROM users;
  - `MIN`は最小値を取得
- SELECT MAX(age) FROM users;
  - `MAX`は最大値を取得

## グループ化

グループ化した結果を集約関数で取得することができるが、
グループ化していないカラムは集約関数で取得することができない

- SELECT age, COUNT(*) FROM users GROUP BY age;
  - `GROUP BY`はグループ化
- SELECT age, COUNT(*) FROM users GROUP BY age HAVING COUNT(*) >= 1;
  - `HAVING`はグループ化した結果に対して条件を指定
- SELECT age, COUNT(*) FROM users WHERE age > 25 GROUP BY age HAVING COUNT(*) >= 1;
  - `HAVING`は`WHERE`よりも後に評価されるため、WHEREにより絞り込んだ結果に対してHAVINGが適用される
  - WHEREでの処理の方が早いため、WHEREを使用できるところは使うようにする

### カラムの型と制約

```sql
CREATE TABLE user (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  age INT CHECK (age >= 0),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

型
- INT: 整数型
- VARCHAR(n): 可変長文字列型。最大n文字まで格納可能
- TIMESTAMP: 日時型。日付と時刻を格納

他にもTEXT、BLOB、DATE、FLOAT、DOUBLEなどRDBによって様々な型がある

型の選択は、データの性質に応じて適切なものを選ぶ必要がある


制約
- 主キー(PRIMARY KEY): テーブル内で一意の値を持つカラム。Unique && NOT NULL 
- AUTO_INCREMENT: 新しい行に一意の識別子を生成できる。INSERT時に自動的に値が増加する
  - idカラムが7まで登録されている場合、次にINSERTするとidは8になる
- 外部キー(FOREIGN KEY): 他のテーブルの主キーを参照するカラム
  - 他テーブルの主キーを参照することでデータのつながりと整合性を保つキー
- UNIQUE: 一意の値を持つことを保証する制約
- NOT NULL: NULL値を許可しない制約
  - 基本的にはNOT NULLをつけることが多い

## 結合(JOIN)

```sql
CREATE TABLE departments (
    dept_id INT PRIMARY KEY,      -- 部署ID: 主キー
    dept_name VARCHAR(50) NOT NULL -- 部署名: NOT NULL制約
);

CREATE TABLE employees (
    emp_id INT PRIMARY KEY,       -- 従業員ID: 主キー
    name VARCHAR(50) NOT NULL,      -- 従業員名: NOT NULL制約
    department_id INT NOT NULL, -- 部署ID: NOT NULL制約
    salary INT
    -- FOREIGN KEY (department_id) REFERENCES departments(dept_id) -- 外部キー制約
    -- 外部キー制約はない想定で、department_idの中にdepartmentsテーブルに存在しない値が入ることもある
);

INSERT INTO departments (dept_id, dept_name) VALUES
(1, 'Sales'),
(2, 'Marketing'),
(3, 'Engineering');

INSERT INTO employees (emp_id, name, department_id, salary) VALUES
(1, 'John Doe', 1, 50000),
(2, 'Jane Smith', 2, 60000),
(3, 'Bob Johnson', 3, 70000),
(4, 'Alice Brown', 4, 55000),
(5, 'Charlie Davis', 1, 65000),
(6, 'Eve Wilson', 2, 72000);
```

- INNER JOIN: 内部結合。両方のテーブルに存在するレコードを取得
  - SELECT emp_id, name, dept_id, dept_name, salary
    FROM employees
    INNER JOIN departments ON employees.department_id = departments.dept_id;
- LEFT JOIN: 左外部結合。左側のテーブルに存在するレコードを全て取得し、右側のテーブルに存在しないレコードはNULLで埋める
  - SELECT emp_id, name, dept_id, dept_name, salary
    FROM employees
    LEFT JOIN departments ON employees.department_id = departments.dept_id;
- RIGHT JOIN: 右外部結合。右側のテーブルに存在するレコードを全て取得し、左側のテーブルに存在しないレコードはNULLで埋める
  - SELECT emp_id, name, dept_id, dept_name, salary
    FROM employees
    RIGHT JOIN departments ON employees.department_id = departments.dept_id;

## サブクエリ

SQL文の内部で入れ子になった別のSQL問い合わせのこと

- SELECT emp_id, name, salary
  FROM employees
  WHERE department_id = (SELECT dept_id FROM departments WHERE dept_name = 'Sales');
  - サブクエリを使用して、Sales部門の従業員を取得
- SELECT name, salary
  FROM employees
  WHERE salary > (
      SELECT AVG(salary)
      FROM employees
  );
  - サブクエリを使用して、全従業員の平均給与よりも高い給与の従業員を取得
- SELECT name
  FROM employees
  WHERE department_id = (
      SELECT dept_id
      FROM departments
      WHERE dept_name = '営業部'
  );
  - サブクエリを使用して、営業部門の従業員を取得
- SELECT dept_count.department_id, dept_count.num_employees
  FROM (
      SELECT department_id, COUNT(*) AS num_employees
      FROM employees
      GROUP BY department_id
  ) AS dept_count
  WHERE dept_count.num_employees >= 2;
  - サブクエリを使用して、各部署の従業員数をカウントし、1人以上の部署を取得
- SELECT E1.name,
  (
      SELECT COUNT(*)
      FROM employees E2
      WHERE E2.salary > E1.salary
  ) AS higher_paid_count
  FROM employees E1;
  - サブクエリを使用して、各従業員よりも高い給与の従業員の数を取得
  - SELECT E1.name, COUNT(E2.emp_id) AS higher_paid_count FROM employees AS E1
    LEFT JOIN employees AS E2 ON E2.salary > E1.salary
    GROUP BY E1.name;
  - サブクエリを使用せずにJOINを使用して同じ結果を取得


## 他

- LIMIT
  - SELECT * FROM users LIMIT 5;
  - `LIMIT`は取得するレコード数を制限
- OFFSET
  - SELECT * FROM users LIMIT 5 OFFSET 2;
  - `OFFSET`は取得するレコードの開始位置を指定
  - OFFSETは読み飛ばすレコード数となるため、大規模なデータセットではパフォーマンスに影響を与えることがある
- DISTINCT
  - SELECT DISTINCT age FROM users;
  - `DISTINCT`は重複を排除して取得
- UNION
  - SELECT name FROM users UNION SELECT name FROM employees;
  - `UNION`は複数のSELECT文の結果を結合
- EXISTS
  - SELECT * FROM users WHERE EXISTS (SELECT * FROM employees WHERE users.id = employees.emp_id);
  - `EXISTS`はサブクエリの結果が存在するかどうかを確認
