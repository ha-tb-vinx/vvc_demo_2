@echo off
rem =======================================
rem MSTB999999 リリース確認処理（マスタ管理）
REM version 3.00 2014/02/26 S.Arakawa:新規
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB999-999 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
