@echo off
rem =======================================
rem MB16-01-03 登録票BY承認データ削除処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB16-01-03 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
