@echo off
rem =======================================
rem MSTB993-041 イニシャルPLU用商品マスタエラーワーク作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB993-041 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
