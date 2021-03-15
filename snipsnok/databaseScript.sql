create database elec5619; -- Creates the new database
create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user
grant all on elec5619.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database