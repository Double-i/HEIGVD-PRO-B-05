insert into tag (name) values("jardin"),("bricolage"),("sport"),("dessin"),("skateboard"),("electricite"),("bois"),("neige"),("ete"),("hiver");

insert into country(country) values("Suisse");

insert into city(city,fk_country) values("Glion",1);
insert into city(city,fk_country) values("Lausanne",1);
insert into city(city,fk_country) values("Martigny",1);
insert into city(city,fk_country) values("Yverdon-Les-Bains",1);
insert into city(city,fk_country) values("Cossonay",1);
insert into city(city,fk_country) values("Nyon",1);

insert into address(address,district,postalcode,lat,lng,fk_city) values ("Route de valmont","Vaud","1823",46.4308093,6.9241496,1);
insert into address(address,district,postalcode,lat,lng,fk_city) values ("Rue des Laurelles 5","Vaud","1304",46.6129732,6.507043,5);
insert into address(address,district,postalcode,lat,lng,fk_city) values ("Maison rouget","Vaud","1400",46.7792522,6.6431692,4);
insert into address(address,district,postalcode,lat,lng,fk_city) values ("Place de la Cathédrale","Vaud","1005",46.5179283,6.6358628,2);
insert into address(address,district,postalcode,lat,lng,fk_city) values ("Avanue de la Gare 5","Valais","1920",46.0972172,7.0747864,3);
insert into address(address,district,postalcode,lat,lng,fk_city) values ("Allée de la Petite Prairie 14","Vaud","1260",46.3884263,6.221818,6);

insert into user (user_name,first_name,last_name,password,is_admin,email,fk_address) values("heymanuel","emmanuel","janssens","$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya",0,"emmanuel.janssens@heig-vd.ch",1);
insert into user (user_name,first_name,last_name,password,is_admin,email,fk_address) values("fukuchimiste","bastien","potet","$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya",0,"bastien.potet@heig-vd.ch",5);
insert into user (user_name,first_name,last_name,password,is_admin,email,fk_address) values("double-i","Ilias","Goujgali","$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya",1,"Ilias.Goujgali@heig-vd.ch",3);
insert into user (user_name,first_name,last_name,password,is_admin,email,fk_address) values("robinr","Robin","Reuteler","$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya",0,"Robin.Reuteler@heig-vd.ch",6);
insert into user (user_name,first_name,last_name,password,is_admin,email,fk_address) values("vitorvaz","Vitor","Vaz Afomzo","$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya",0,"Vitor.vazafonzo@heig-vd.ch",2);
insert into user (user_name,first_name,last_name,password,is_admin,email,fk_address) values("mauricel","Maurice","Lehman","$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya",0,"Maurice.Lehman@heig-vd.ch",4);

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

insert into ezobject_tag(fk_tag,fk_ezobject) values("jardin",1);
insert into ezobject_tag(fk_tag,fk_ezobject) values("bricolage",1);
insert into ezobject_tag(fk_tag,fk_ezobject) values("bricolage",2);
insert into ezobject_tag(fk_tag,fk_ezobject) values("bricolage",9);
insert into ezobject_tag(fk_tag,fk_ezobject) values("bricolage",13);
insert into ezobject_tag(fk_tag,fk_ezobject) values("sport",4);
insert into ezobject_tag(fk_tag,fk_ezobject) values("skateboard",4);
insert into ezobject_tag(fk_tag,fk_ezobject) values("ete",1);
insert into ezobject_tag(fk_tag,fk_ezobject) values("ete",8);
