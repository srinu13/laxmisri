                              
   INSERT INTO user_details (`id`, `email_id`,`password`,`remarks`,`status`,`user_name`,`user_role`)
SELECT 1,
       'admin@femto.com',
       'admin',
        'add admin',
        'active',
        'admin',
        'Administrator'
       
FROM dual
WHERE NOT EXISTS
    (SELECT *	
     FROM user_details
     WHERE user_name = 'admin');
     
                             
   INSERT INTO user_details (`id`, `email_id`,`password`,`remarks`,`status`,`user_name`,`user_role`)
SELECT 2,
       'admin@femto.com',
       'femto',
        'add admin',
        'active',
        'femto',
        'user'
       
FROM dual
WHERE NOT EXISTS
    (SELECT *	
     FROM user_details
     WHERE user_name = 'femto');  
     
                           
   INSERT INTO user_roles (`id`,`user_role`)
SELECT 1,
        'Administrator'
FROM dual
WHERE NOT EXISTS
    (SELECT *	
     FROM user_roles
     WHERE user_role = 'Administrator');     
                           
   INSERT INTO user_roles (`id`,`user_role`)
SELECT 2,
        'user'
FROM dual
WHERE NOT EXISTS
    (SELECT *	
     FROM user_roles
     WHERE user_role = 'user');     
          
     
     
     
     