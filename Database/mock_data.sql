insert into user (userName,firstName,lastName,password,isAdmin,email) values("heymanuel","emmanuel","janssens","password",0,"emmanuel.janssens@heig-vd.ch");
insert into user (userName,firstName,lastName,password,isAdmin,email) values("fukuchimiste","bastien","potet","password",0,"bastien.potet@heig-vd.ch");
insert into user (userName,firstName,lastName,password,isAdmin,email) values("double-i","Ilias","Goujgali","password",1,"Ilias.Goujgali@heig-vd.ch");
insert into user (userName,firstName,lastName,password,isAdmin,email) values("robinr","Robin","Reuteler","password",0,"Robin.Reuteler@heig-vd.ch");
insert into user (userName,firstName,lastName,password,isAdmin,email) values("vitorvaz","Vitor","Vaz Afomzo","password",0,"Vitor.vazafonzo@heig-vd.ch");
insert into user (userName,firstName,lastName,password,isAdmin,email) values("mauricel","Maurice","Lehman","password",0,"Maurice.Lehman@heig-vd.ch");

insert into tag (name) values("jardin"),("bricolage"),("sport"),("dessin"),("skateboard"),("electricite"),("bois"),("neige"),("ete"),("hiver");

insert into ezobject (name,description,owner) values("pelle","pelle de jardinage en bon etat","fukuchimiste");
insert into ezobject (name,description,owner) values("perceuse","super perceuse","double-i");
insert into ezobject (name,description,owner) values("arc","un arc de bonne qualité","vitorvaz");
insert into ezobject (name,description,owner) values("skateboard","un skate board de bonne qualité peu utilise","heymanuel");
insert into ezobject (name,description,owner) values("raquette de ping pong","peu utilisé","fukuchimiste");
insert into ezobject (name,description,owner) values("guitare","guitare classique","mauricel");
insert into ezobject (name,description,owner) values("jumelle","de bonnes jumelles","robinr");
insert into ezobject (name,description,owner) values("sac a dos","sac a dos de randonnee","heymanuel");
insert into ezobject (name,description,owner) values("scie","fournis avec des lames","double-i");
insert into ezobject (name,description,owner) values("raclette ","pour vos diner en montagne","robinr");
insert into ezobject (name,description,owner) values("drapeau suisse","montrez votre fiereté nimporte ou vous allez","fukuchimiste");
insert into ezobject (name,description,owner) values("haut parleur","besoin de musique pour vos soirées?","heymanuel");
insert into ezobject (name,description,owner) values("agrapheuse","puissant ","mauricel");

insert into ezobjecttag(fkTag,fkEZObject) values(1,1);
insert into ezobjecttag(fkTag,fkEZObject) values(2,1);
insert into ezobjecttag(fkTag,fkEZObject) values(2,2);
insert into ezobjecttag(fkTag,fkEZObject) values(2,9);
insert into ezobjecttag(fkTag,fkEZObject) values(2,13);
insert into ezobjecttag(fkTag,fkEZObject) values(3,4);
insert into ezobjecttag(fkTag,fkEZObject) values(5,4);
insert into ezobjecttag(fkTag,fkEZObject) values(9,1);
insert into ezobjecttag(fkTag,fkEZObject) values(9,8);

insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-03-30","2020-04-10",null,"pending","vitorvaz",1);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-04-10","2020-05-1",null,"pending","heymanuel",11);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-04-20","2020-05-2",null,"pending","double-i",7);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-05-20","2020-06-1",null,"pending","robinr",9);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-03-27","2020-04-3",null,"pending","mauricel",10);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-08-10","2020-08-20",null,"pending","fukuchimiste",2);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-04-27","2020-05-7",null,"pending","heymanuel",3);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-10-2","2020-10-10",null,"pending","double-i",8);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-8-1","2020-8-13",null,"pending","vitorvaz",12);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-9-5","2020-9-20",null,"pending","mauricel",5);