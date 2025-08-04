@echo off
rem =======================================
rem MB16-01-02 不要レコード物理削除処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB16-01-02 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
