@echo off
REM ============================================================================
REM タイトル：オンライン特売チェックマスタ作成
REM 説明　　：オンラインで特売チェックを行う為の必要なマスタ作成を行う。
REM version 3.00 2013/05/23 M.Ayukawa:新規
REM ============================================================================

call %~dp0\SetBatchPath.bat

REM == MB83-01-51 TMP BATマスタ作成処理
%execjava% mdware.common.batch.util.control.BatchController MB83-01-51 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

REM == MB83-02-51 店別商品展開処理
%execjava% mdware.common.batch.util.control.BatchController MB83-02-51 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

REM == MB83-04-51 店別例外反映処理
%execjava% mdware.common.batch.util.control.BatchController MB83-04-51 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

REM == MBA2-18-51 IF商品マスタ作成
%execjava% mdware.common.batch.util.control.BatchController MBA2-18-51 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

REM == MBA2-22-51 IF店別商品データ作成処理
%execjava% mdware.common.batch.util.control.BatchController MBA2-22-51 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

:end

exit 0

:error

exit 1
