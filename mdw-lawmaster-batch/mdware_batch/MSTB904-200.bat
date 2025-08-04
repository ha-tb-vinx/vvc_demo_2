@echo off
REM =======================================
REM タイトル：DWH用マスタトリガーファイル作成処理
REM 説明	：マスタ管理のDWH用トリガーファイルを0バイトで作成する
REM version 3.00 2014/02/21 S.Arakawa:新規作成
REM =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-200 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
