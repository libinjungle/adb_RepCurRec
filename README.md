# adb_RepCurRec

This is a class project of NYU's advanced database class. 

Goal:

Design and implement a distributed database, complete with multiversion concurrency control, 
deadlock avoidance, replication, and failure recovery.

Implementation:

A transaction has a list of lock.
A site has a lock table that maps variable to locks on it
A lock has a transaction, site, variable associated with it.
