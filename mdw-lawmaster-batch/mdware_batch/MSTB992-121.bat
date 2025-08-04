@echo off
rem =======================================
rem MSTB992-121 POS用種別マスタバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-121 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
