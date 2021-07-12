update pronostici set link ='https://docs.google.com/spreadsheets/d/1E2tfZwRvsbnqf0RZnN9elyclz8mXeHAq/view?usp=sharing' where id_giocatore =(select id from giocatori where nome = 'garau');

