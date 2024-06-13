#!pip install TA_Lib-0.4.29-cp312-cp312-win_amd64.whl

import tkinter as tk
from tkinter import ttk, messagebox
import yfinance as yf
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
import math
import talib as ta
import threading


def calculate_RSI(data):
    change = data.diff()
    change.dropna(inplace=True)
    change_up = change.copy()
    change_down = change.copy()
    change_up[change_up<0] = 0
    change_down[change_down>0] = 0
    change.equals(change_up+change_down)
    avg_up = change_up.rolling(14).mean()
    avg_down = change_down.rolling(14).mean().abs()
    rsi = 100 * avg_up / (avg_up + avg_down)
    return rsi

def generateTillsonT3(close_array, high_array, low_array, volume_factor, t3Length):
    ema_first_input = (high_array + low_array + 2 * close_array) / 4
    e1 = ta.EMA(ema_first_input, t3Length)
    e2 = ta.EMA(e1, t3Length)
    e3 = ta.EMA(e2, t3Length)
    e4 = ta.EMA(e3, t3Length)
    e5 = ta.EMA(e4, t3Length)
    e6 = ta.EMA(e5, t3Length)
    c1 = -1 * volume_factor * volume_factor * volume_factor
    c2 = 3 * volume_factor * volume_factor + 3 * volume_factor * volume_factor * volume_factor
    c3 = -6 * volume_factor * volume_factor - 3 * volume_factor - 3 * volume_factor * volume_factor * volume_factor
    c4 = 1 + 3 * volume_factor + volume_factor * volume_factor * volume_factor + 3 * volume_factor * volume_factor
    T3 = c1 * e6 + c2 * e5 + c3 * e4 + c4 * e3
    return T3

def calculate_mavi(hl):
    m10=ta.WMA(hl,3)
    m20=ta.WMA(m10,5)
    m30=ta.WMA(m20,8)
    m40=ta.WMA(m30,13)
    m50=ta.WMA(m40,21)
    MAVW=ta.WMA(m50,34)
    return MAVW

def calculate_superTrend(data,period,multiplier):
    atr = ta.ATR(data["High"], data["Low"], data["Close"], period)
    previous_final_upperband = 0
    previous_final_lowerband = 0
    final_upperband = 0
    final_lowerband = 0
    previous_close = 0
    previous_supertrend = 0
    supertrend = []
    supertrendc = 0
    for i in range(0, len(data.Close)):
       if np.isnan(data.Close[i]):
           pass
       else:
           highc = data.High[i]
           lowc = data.Low[i]
           atrc = atr[i]
           closec = data.Close[i]

           if math.isnan(atrc):
               atrc = 0

           basic_upperband = (highc + lowc) / 2 + multiplier * atrc
           basic_lowerband = (highc + lowc) / 2 - multiplier * atrc

           if basic_upperband < previous_final_upperband or previous_close > previous_final_upperband:
               final_upperband = basic_upperband
           else:
               final_upperband = previous_final_upperband

           if basic_lowerband > previous_final_lowerband or previous_close < previous_final_lowerband:
               final_lowerband = basic_lowerband
           else:
               final_lowerband = previous_final_lowerband

           if previous_supertrend == previous_final_upperband and closec <= final_upperband:
               supertrendc = final_upperband
           elif previous_supertrend == previous_final_upperband and closec >= final_upperband:
               supertrendc = final_lowerband
           elif previous_supertrend == previous_final_lowerband and closec >= final_lowerband:
               supertrendc = final_lowerband
           elif previous_supertrend == previous_final_lowerband and closec <= final_lowerband:
               supertrendc = final_upperband

           supertrend.append(supertrendc)
           previous_close = closec
           previous_final_upperband = final_upperband
           previous_final_lowerband = final_lowerband
           previous_supertrend = supertrendc
    return supertrend

def evaluate(model, test_features, test_labels):
    predictions = model.predict(test_features)
    errors = abs(predictions - test_labels)
    mape = 100 * np.mean(errors / test_labels)
    accuracy = 100 - mape
    print('Model Performance')
    print('Average Error: {:0.4f}  '.format(np.mean(errors)))
    print('Accuracy = {:0.2f}%.'.format(accuracy))
    return accuracy, predictions

def predict(stock_name,data):
    d={}
    i=stock_name
    d[i]=data
    d[i]["hl"]=(d[i]["High"]+d[i]["Low"])/2
    del d[i]["Dividends"]
    del d[i]["Stock Splits"]
    d[i]["Tomorrow"]=d[i]["Close"].shift(-1)
    d[i].drop(index=d[i].index[:100], axis=0, inplace=True)
    d[i]["HL Diff"] = d[i]["High"]-d[i]["Low"]
    d[i]["CO Diff"]= d[i]["Open"]-d[i]["Close"]
    d[i]["MA5"]=d[i]["hl"].rolling(5).mean()
    d[i]["MA10"]=d[i]["hl"].rolling(10).mean()
    d[i]["MA15"]=d[i]["hl"].rolling(15).mean()
    d[i]["MA20"]=d[i]["hl"].rolling(20).mean()
    d[i]["Standard Deviation"]=d[i]["hl"].rolling(5).std()
    d[i].drop(index=d[i].index[:20], axis=0, inplace=True)
    d[i]["RSI"]=calculate_RSI(d[i]["Close"])
    d[i]["T3"]=generateTillsonT3(d[i]["Close"],d[i]["High"],d[i]["Low"],0.618,3)
    d[i]["Mavilim"]=calculate_mavi(d[i]["hl"])
    d[i]["SuperTrend"]=calculate_superTrend(d[i],10,3)
    d[i].drop(index=d[i].index[:100], axis=0, inplace=True)

    d_norm={}
    d_norm[stock_name]=(d[stock_name]-d[stock_name].mean())/d[stock_name].std()
    d_norm[stock_name].drop(index=d_norm[stock_name].index[-1:], axis=0, inplace=True)
    
    features = d_norm[stock_name].drop(['Tomorrow'], axis = 1)
    labels= d_norm[stock_name]['Tomorrow']


    d[stock_name].drop(index=d[stock_name].index[:2500], axis=0, inplace=True)
    d[stock_name].drop(index=d[stock_name].index[-1:], axis=0, inplace=True)
    features = d[stock_name].drop(['Tomorrow'], axis = 1)
    labels= d[stock_name]['Tomorrow']


    X_train, X_test, y_train, y_test = train_test_split(
     features, labels, train_size =0.80 ,test_size=0.20,shuffle=False)

    xt=X_test.tail(1)
   
    lin_reg = LinearRegression().fit(X_train, y_train)
    lin_reg_pred = lin_reg.predict(xt)

    return lin_reg_pred,y_test.tail(1).values[0]


def submit_click():
    global loading_label
    ticker = ticker_entry.get()

    loading_label = ttk.Label(window, text="                             Calculating...                             ")
    loading_label.grid(row=2, column=0, columnspan=2, padx=5, pady=5)
    
    threading.Thread(target=lambda: predict_and_update(ticker)).start()

def predict_and_update(ticker):
    try:
        ticker_object = yf.Ticker(ticker)
        all_data = ticker_object.history(period="max")
       
        data_30d = all_data.tail(30)

        predicted_price,prevPrice = predict(ticker,  all_data)

        direction="Flat"
        if(prevPrice>predicted_price):direction="Down"
        elif(predicted_price>prevPrice):direction="up"

        window.after(0, lambda: result_label.config(text=f"Price prediction for tomorrow:  {predicted_price[0]:.2f} $({direction})"))

        figure = plt.Figure(figsize=(8, 6), dpi=100)
        ax = figure.add_subplot(111)
        ax.plot(data_30d.index, data_30d["Close"])
        ax.set_title(f"{ticker} Price Chart")
        ax.set_xlabel("History")
        ax.set_ylabel("Price")

        chart = FigureCanvasTkAgg(figure, master=window)
        chart.draw()
        window.after(0, lambda: chart.get_tk_widget().grid(row=3, column=0, columnspan=2, padx=5, pady=5))

        window.after(0, lambda: loading_label.destroy())

    except Exception as e:
        window.after(0, lambda: messagebox.showerror("Error", f"Stock could not be found or data could not be retrieved: {e}"))
        window.after(0, lambda: loading_label.destroy())


window = tk.Tk()
window.title("Stock Prediction App")
ticker_label = ttk.Label(window, text="Stock Name:")
ticker_label.grid(row=0, column=0, padx=5, pady=5)
ticker_entry = ttk.Entry(window)
ticker_entry.grid(row=0, column=1, padx=5, pady=5)
submit_button = ttk.Button(window, text="Predict", command=submit_click)
submit_button.grid(row=1, column=0, columnspan=2, padx=5, pady=5)
result_label = ttk.Label(window, text="")
result_label.grid(row=2, column=0, columnspan=2, padx=5, pady=5)

window.mainloop()

