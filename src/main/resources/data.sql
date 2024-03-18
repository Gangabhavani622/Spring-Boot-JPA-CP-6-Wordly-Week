insert into writer(name, bio) VALUES('John Doe',	'Famous writer of fantasy tales');
insert into writer(name, bio) VALUES('Jane Smith',	'Renowned journalist and editor');
insert into writer(name, bio) VALUES('Emily Brontë',	'Author of Wuthering Heights');
insert into writer(name, bio) VALUES('Ernest Hemingway',	'Nobel Prize-winning author known for works like The Old Man and the Sea');

insert into magazine(title, publicationDate) VALUES('Fantasy Tales',	'2023-10-05');
insert into magazine(title, publicationDate) VALUES('Journalist Weekly',	'2023-09-15');
insert into magazine(title, publicationDate) VALUES('Classic Literature Monthly',	'2023-10-15');
insert into magazine(title, publicationDate) VALUES('Modern Writers Digest',	'2023-09-20');

insert into writer_magazine(writerId, magazineId) VALUES(1, 1);
insert into writer_magazine(writerId, magazineId) VALUES(1, 2);
insert into writer_magazine(writerId, magazineId) VALUES(2, 2);
insert into writer_magazine(writerId, magazineId) VALUES(3, 3);
insert into writer_magazine(writerId, magazineId) VALUES(4, 3);
insert into writer_magazine(writerId, magazineId) VALUES(4, 4);

