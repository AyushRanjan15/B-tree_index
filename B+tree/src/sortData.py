import pandas as pd

data = pd.read_csv("data.csv")


m_num = {'January': 1, 'December': 12, 'October': 10, 'November': 11, 'September': 9, 'July': 7,
         'August': 8, 'February': 2, 'May': 5, 'June': 6, 'March': 3, 'April': 4}

data['Mnumber'] = data['Month']

data['Mnumber'] = data['Mnumber'].replace(m_num)

data_sorted = data.sort_values(
    ['Sensor_ID', 'Year', 'Mnumber', 'Mdate', 'Time'])

data_sorted = data_sorted.reset_index()

data_sorted = data_sorted.drop(['Mnumber', 'index'], axis=1)

data_sorted.to_csv("clustered.csv", index=False)
