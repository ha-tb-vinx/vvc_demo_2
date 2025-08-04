REM @echo off
REM ============================================================================
REM タイトル：ハンパー売上実績データ作成
REM 説明    ：Hレコードからハンパー商品売上実績を作成する。
REM version 1.00 2016/11/01 J.Endo:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB301010
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
