@echo off
rem =======================================
rem MBA0-03-01 新店用店ＤＰＴマスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA0-03-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
