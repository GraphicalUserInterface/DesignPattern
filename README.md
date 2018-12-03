# DesignPattern
Session 2 to design the GUI
Group 2
1.Zhiheng Wu
2.Ge Qiu
3.Shuyu Chen

SESSION 3 :
create a JSON file containing the tems to store into the inventory
add a button into the GUI to load a file
New feature : the inventory can contains few items of each kind
Add a piechart to the GUI (number of item by type)

SESSION 4 :
each item has its own creation date (i.e. you can have a dexterity vest buy on monday and another one buy 3 days later)
Add a barchart where X-axis is the sellIn and the Y-axis is the number of items
Add a barchart where X-axis is the creation date and the Y-axis is the number of items (i.e. 3 items were created on monday the 26th of November)

In this tag, we finish the basic function of Adding a barchart which can change itself automatically.
But still get some problems like:
When using "set1.getData().add(new XYChart.Data<>(sell[i], numb[i]))" to show the figure of barchart,
We change the X label element to String type to fulfill the demand of "Data<>(String,int)",
so we can make the X label element in  right order of number.