@echo off
rem =======================================
rem MSTB920-070 緊急POS用種別マスタバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB920-070 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
