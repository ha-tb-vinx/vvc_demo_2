@echo off
rem =======================================
rem MBA3-18-01 Information analysis for the product master ASN creation process (distribution to product performance side)
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA3-18-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

