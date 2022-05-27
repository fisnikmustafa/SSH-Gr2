create table students(
  id int(11) NOT NULL auto_increment,
  first_name text NOT NULL,
  parent_name text NOT NULL,
  last_name text NOT NULL,
  email varchar(200) NOT NULL unique,
  password text NOT NULL,
  gender ENUM('F', 'M') NOT NULL,
  school_name text NOT NULL,
  grades json NOT NULL,
  phone_number varchar(20) unique,
  is_online boolean NOT NULL default 0,
  picture_path text NOT NULL,
  primary key(Id)
);


insert into students values (10200, 'Arsim', 'Bekim', 'Shabani', 'arsimshabani@gmail.com', 'arsim.shabani', 'M', 'Frang Bardhi', '{"shqip":5, "anglisht":5, "matematike":4, "fizike":4, "kimi":5, "biologji":5, "filozofi":5}', "044121212", 0, "student1");
insert into students values (10201, 'Festim', 'Agim', 'Azemi', 'festimazemi@gmail.com', 'festim.azemi', 'M', 'Xhevdet Doda', '{"shqip":5, "anglisht":5, "matematike":5, "fizike":5, "kimi":5, "biologji":5, "filozofi":5}', "044321123", 0, "student2");
insert into students values (10202, 'Engjell', 'Selami', 'Aliu', 'engjellaliu@gmail.com', 'engjell.aliu', 'M', 'Gjin Gazulli', '{"shqip":4, "anglisht":3, "matematike":4, "fizike":5, "kimi":5, "biologji":5, "filozofi":5}', "049998877", 0, "student3");
insert into students values (10203, 'Agon', 'Faton', 'Halimi', 'agonhalimi@gmail.com', 'agon.halimi', 'M', 'Sami Frasheri', '{"shqip":5, "anglisht":5, "matematike":4, "fizike":4, "kimi":3, "biologji":4, "filozofi":5}', "044267228", 0, "student4");
insert into students values (10204, 'Besa', 'Agim', 'Shabani', 'besashabani@gmail.com', 'besa.shabani', 'F', 'Bedri Pejani', '{"shqip":3, "anglisht":3, "matematike":2, "fizike":3, "kimi":3, "biologji":4, "filozofi":4}', "049125212", 0, "student5");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10205, 'Arlind', 'Ferid', 'Rexhepi', 'arlindrexhepi@gmail.com', 'arlind.rexhepi', 'M', 'Zenel Hajdini', '{"shqip":2, "anglisht":3, "matematike":1, "fizike":2, "kimi":2, "biologji":3, "filozofi":4}', 0, "student6");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10206, 'Rina', 'Lavdim', 'Ademi', 'rinaademi@gmail.com', 'rina.ademi', 'F', 'Hajdar Dushi', '{"shqip":5, "anglisht":5, "matematike":4, "fizike":4, "kimi":5, "biologji":5, "filozofi":5}', 0, "student7");

insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10207, 'Arijon', 'Avdyl', 'Sulejmani', 'arijonsulejmani@gmail.com', 'arijon.sulejmani', 'M', 'Frang Bardhi', '{"shqip":4, "anglisht":4, "matematike":4, "fizike":4, "kimi":4, "biologji":5, "filozofi":5}', 0, "student1");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10208, 'Feston', 'Ramiz', 'Ademi', 'festonademi@gmail.com', 'feston.ademi', 'M', 'Xhevdet Doda', '{"shqip":5, "anglisht":5, "matematike":5, "fizike":4, "kimi":4, "biologji":5, "filozofi":5}', 0, "student2");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10209, 'Fidan', 'Agron', 'Tahiri', 'fidantahiri@gmail.com', 'fidan.tahiri', 'M', 'Gjin Gazulli', '{"shqip":5, "anglisht":5, "matematike":5, "fizike":5, "kimi":4, "biologji":5, "filozofi":4}', 0, "student3");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10210, 'Ardian', 'Agon', 'Halimi', 'ardianhalimi@gmail.com', 'ardian.halimi', 'M', 'Sami Frasheri', '{"shqip":5, "anglisht":4, "matematike":4, "fizike":5, "kimi":5, "biologji":5, "filozofi":3}', 0, "student4");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10211, 'Besart', 'Afrim', 'Sinani', 'besartsinani@gmail.com', 'besart.sinani', 'M', 'Bedri Pejani', '{"shqip":5, "anglisht":5, "matematike":5, "fizike":4, "kimi":4, "biologji":5, "filozofi":5}', 0, "student6");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10212, 'Bardhyl', 'Bekim', 'Rexhepi', 'bardhylrexhepi@gmail.com', 'bardhyl.rexhepi', 'M', 'Zenel Hajdini', '{"shqip":3, "anglisht":4, "matematike":2, "fizike":3, "kimi":2, "biologji":5, "filozofi":5}', 0, "student4");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10213, 'Taulant', 'Sokol', 'Mustafa', 'taulantmustafa@gmail.com', 'taulant.mustafa', 'M', 'Hajdar Dushi', '{"shqip":5, "anglisht":5, "matematike":4, "fizike":5, "kimi":3, "biologji":4, "filozofi":5}', 0, "student2");

insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10214, 'Kaltrina', 'Fisnik', 'Mehmeti', 'kaltrinamehmeti@gmail.com', 'kaltrina.mehmeti', 'F', 'Frang Bardhi', '{"shqip":5, "anglisht":5, "matematike":4, "fizike":4, "kimi":5, "biologji":5, "filozofi":5}', 0, "student8");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10215, 'Saranda', 'Ali', 'Kadriu', 'sarandakadriu@gmail.com', 'saranda.kadriu', 'F', 'Xhevdet Doda', '{"shqip":5, "anglisht":5, "matematike":5, "fizike":5, "kimi":5, "biologji":5, "filozofi":5}', 0, "student9");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10216, 'Arlinda', 'Kadri', 'Aliu', 'arlindaaliu@gmail.com', 'arlinda.aliu', 'F', 'Gjin Gazulli', '{"shqip":4, "anglisht":3, "matematike":4, "fizike":5, "kimi":5, "biologji":5, "filozofi":5}', 0, "student10");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10217, 'Edona', 'Agon', 'Mustafa', 'edonamustafa@gmail.com', 'edona.mustafa', 'F', 'Sami Frasheri', '{"shqip":5, "anglisht":5, "matematike":4, "fizike":4, "kimi":3, "biologji":4, "filozofi":5}', 0, "student8");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10218, 'Esra', 'Shaban', 'Sokoli', 'esrasokoli@gmail.com', 'esra.sokoli', 'F', 'Bedri Pejani', '{"shqip":3, "anglisht":3, "matematike":2, "fizike":3, "kimi":3, "biologji":4, "filozofi":4}', 0, "student9");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10219, 'Olta', 'Luan', 'Vranovci', 'oltavranovci@gmail.com', 'olta.vranovci', 'F', 'Zenel Hajdini', '{"shqip":2, "anglisht":3, "matematike":1, "fizike":2, "kimi":2, "biologji":3, "filozofi":4}', 0, "student10");
insert into students (id,first_name,parent_name,last_name,email,password,gender,school_name,grades,is_online,picture_path) values (10220, 'Altina', 'Valon', 'Halimi', 'altinahalimi@gmail.com', 'altina.halimi', 'F', 'Hajdar Dushi', '{"shqip":5, "anglisht":5, "matematike":4, "fizike":4, "kimi":5, "biologji":5, "filozofi":5}', 0, "student7");


