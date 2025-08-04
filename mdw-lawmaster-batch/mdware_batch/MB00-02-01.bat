@echo off
rem =======================================
rem MB00-02-01 オンラインフラグ更新(不可)
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB00-02-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
