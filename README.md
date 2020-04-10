# The-Quest-of-Legends


"javac Main.java"  
"java Main"  


Class Design:  

Interface:
 - DataInitialize
 - Encounter
 - Fight
 - Regeneration
 - BuyorSell

Cells
   - PlainCells (CommonCells)
   - InAccessibleCells (NonAccessibleCells)
   - NexusCells/Markets
   - BushCells
   - KoulouCells
   - CaveCells

Roles
   - Heroes
     - Warriors
     - Paladins
     - Sorcerers
   - Monsters
     - Dragons
     - Exoskeletons
     - Spirits

Items
   - Weapons
   - Armors
   - Spells
     - IceSpells
     - FileSpells
     - LigntningSpells
   - Potions

Map
   - TheQuestMap
   - TheQuestOfLegendsMap

Teams
   - TeamTheQuest
   - TeamTheQuestOfLegends

Simulation
   - TheQuestSimulation
   - TheQuestOfLegendsSimulation

Main  
Backpack  
Probability  
Rounds  
ZshColors  

Assume that hero won't fight with monster when they encounter at a Nexus cell
