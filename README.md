IMEP：Intergrated Model Evaluation Platform
============================================

[![Join the chat at https://gitter.im/meteoinfo/community](https://badges.gitter.im/meteoinfo/community/meteoinfo.svg)](https://gitter.im/meteoinfo/community)

Installation
------------

MeteoInfo need to be pre-installed. The MeteoInfo and IMEP can be downloaded from 
http://www.meteothink.org/downloads/index.html. Unzip and copy "imep" folder into "MeteoInfo -> toolbox" folder.

Features
--------

IMEP is a model evaluation toolbox of MeteoInfo. The methods of ``continuous``, ``dichotomous``, 
``multicategory`` and ``score`` are supported. 

Running
-------

IMEP jython script can be running in MeteoInfoLab environment.

Example
-------

Continuous and dichotomous verification:

```python
import imep

datadir = 'D:/MyProgram/java/MeteoInfoDev/toolbox/IMEP/imep/sample'
fn = os.path.join(datadir, 'ex1.csv')
table = DataFrame.read_table(fn, delimiter=',', format='%3i%5f%i')
obs = table['Obs'].values
fcst = table['GFSMean'].values

print 'Continuous verification...'
cmethod = imep.verifymethod(method='continuous')
ctable = imep.verifytable(obs, fcst, cmethod)
print ctable

print 'Dichotomous verification...'
dr = imep.datarange(min=8)    # >= 8
dmethod = imep.verifymethod(method='dichotomous', drange=dr)
dtable = imep.verifytable(obs, fcst, dmethod)
print dtable
```
    
Output from the script:

```python
>>> run script...
Continuous verification...
Score	Value
MAE	2.20
R	0.69
MeanErr	0.58
Bias	1.09
RMSE	2.76
MSE	7.62

Dichotomous verification...
Hit: 58
Miss: 24
False alarm: 30
Correct negative: 123
---------------------------
Score	Value
Accuracy	0.77
HK	0.51
OR	9.91
POD	0.71
ETS	0.34
Bias	1.07
FAR	0.34
ORSS	0.82
POFD	0.20
HSS	0.50
SR	0.66
TS	0.52
```

Documentation
-------------

Learn more about MeteoInfo and IMEP in its official documentation at http://meteothink.org/

License
-------

Copyright 2020, IMEP Developers

Licensed under the LGPL License, Version 3.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.gnu.org/licenses/lgpl.html

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.