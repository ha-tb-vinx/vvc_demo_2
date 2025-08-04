@echo off
rem =======================================
rem IKO-01-01　移行商品マスタチェックデジットチェック
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController IKO-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
