@echo off
rem =======================================
rem HSKB041060 POS用特売マスタファイルバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController HSKB041060 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
