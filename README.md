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


FINAL (transactions) :
Buy items to suppliers (add item to the stock)
Sell an item to a client (remove item from the stock)
Note : keep an audit trail of what you sold and bought
Add a barchart where X-axis is the time and Y-axis is the number of items
1st serie = number of buy
2nd serie = number of sell
OPTION : if you want you can manage prices, in this case you have the price you have paid the item and the price you have sell the item.
 The difference is the P&L (profit and loss) that you can sum to know how many cash did you earn.
 
 In this tag, we finish the basic function of buying and selling the items using mouseClick events.
 Also the function of tracing the price we have bought and sold, then showing the total profits in a listview.
 But we have some bugs with the Barchart that it cannot show the latest xlabel in the third barchart.
 (For example when in the day3, it only shows the last day1 and day2, but the data of day 3 is showned above)
